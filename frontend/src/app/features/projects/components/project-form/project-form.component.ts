import { Component, OnInit, inject, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { ProjectStateService } from '../../../../core/services/project-state.service';
import { CreateProjectRequest, Project, ProjectStatus } from '../../../../core/models/project.model';

@Component({
  selector: 'app-project-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatIconModule
  ],
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.scss']
})
export class ProjectFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<ProjectFormComponent>);
  private projectState = inject(ProjectStateService);

  projectForm!: FormGroup;
  isSubmitting = false;
  isEditMode = false;
  projectId?: string;

  statuses = [
    { value: ProjectStatus.DRAFT, label: 'Draft' },
    { value: ProjectStatus.ACTIVE, label: 'Active' },
    { value: ProjectStatus.COMPLETED, label: 'Completed' }
  ];

  constructor(@Inject(MAT_DIALOG_DATA) public data: { project?: Project }) {
    if (data?.project) {
      this.isEditMode = true;
      this.projectId = data.project.id;
    }
  }

  ngOnInit(): void {
    this.initForm();
    if (this.isEditMode && this.data.project) {
      this.populateForm(this.data.project);
    }
  }

  private initForm(): void {
    const today = new Date();
    const nextMonth = new Date();
    nextMonth.setMonth(nextMonth.getMonth() + 1);

    this.projectForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
      budget: [0, [Validators.required, Validators.min(0.01), Validators.max(999999999.99)]],
      currency: ['EUR', [Validators.required, Validators.pattern(/^[A-Z]{3}$/)]],
      startDate: [today, [Validators.required]],
      endDate: [nextMonth, [Validators.required]],
      status: [ProjectStatus.DRAFT, [Validators.required]],
      departmentId: ['00000000-0000-0000-0000-000000000001', [Validators.required]],
      projectManagerId: ['00000000-0000-0000-0000-000000000001', [Validators.required]]
    }, { validators: this.dateRangeValidator });
  }

  private dateRangeValidator(group: FormGroup): { [key: string]: boolean } | null {
    const startDate = group.get('startDate')?.value;
    const endDate = group.get('endDate')?.value;

    if (startDate && endDate && startDate > endDate) {
      return { dateRange: true };
    }
    return null;
  }

  private populateForm(project: Project): void {
    this.projectForm.patchValue({
      title: project.title,
      description: project.description,
      budget: project.budget,
      currency: project.currency,
      startDate: new Date(project.startDate),
      endDate: new Date(project.endDate),
      status: project.status,
      departmentId: project.departmentId,
      projectManagerId: project.projectManagerId
    });
  }

  onSubmit(): void {
    if (this.projectForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;

      const formValue = this.projectForm.value;
      const request: CreateProjectRequest = {
        ...formValue,
        startDate: this.formatDate(formValue.startDate),
        endDate: this.formatDate(formValue.endDate)
      };

      if (this.isEditMode && this.projectId) {
        // Update existing project
        this.projectState.updateProject(this.projectId, request).subscribe({
          next: () => {
            this.dialogRef.close(true);
          },
          error: (error) => {
            console.error('Error updating project:', error);
            this.isSubmitting = false;
          }
        });
      } else {
        // Create new project
        this.projectState.createProject(request).subscribe({
          next: () => {
            this.dialogRef.close(true);
          },
          error: (error) => {
            console.error('Error creating project:', error);
            this.isSubmitting = false;
          }
        });
      }
    }
  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  getErrorMessage(fieldName: string): string {
    const field = this.projectForm.get(fieldName);
    if (!field || !field.errors) return '';

    if (field.errors['required']) return `${this.getFieldLabel(fieldName)} is required`;
    if (field.errors['minlength']) return `Minimum length is ${field.errors['minlength'].requiredLength}`;
    if (field.errors['maxlength']) return `Maximum length is ${field.errors['maxlength'].requiredLength}`;
    if (field.errors['min']) return `Minimum value is ${field.errors['min'].min}`;
    if (field.errors['max']) return `Maximum value is ${field.errors['max'].max}`;
    if (field.errors['pattern']) return 'Invalid format';

    return '';
  }

  private getFieldLabel(fieldName: string): string {
    const labels: { [key: string]: string } = {
      title: 'Title',
      description: 'Description',
      budget: 'Budget',
      currency: 'Currency',
      startDate: 'Start Date',
      endDate: 'End Date',
      status: 'Status'
    };
    return labels[fieldName] || fieldName;
  }

  get hasDateRangeError(): boolean {
    return this.projectForm.hasError('dateRange') &&
           this.projectForm.get('startDate')?.touched === true &&
           this.projectForm.get('endDate')?.touched === true;
  }
}
