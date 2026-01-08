import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ProjectStateService } from '../../../core/services/project-state.service';
import { ProjectFormComponent } from '../components/project-form/project-form.component';
import { Project } from '../../../core/models/project.model';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatTooltipModule
  ],
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss']
})
export class ProjectListComponent implements OnInit {
  private projectState = inject(ProjectStateService);
  private dialog = inject(MatDialog);

  projects = this.projectState.projects;
  loading = this.projectState.loading;

  displayedColumns: string[] = ['title', 'description', 'budget', 'status', 'startDate', 'endDate', 'actions'];

  ngOnInit(): void {
    this.projectState.refreshData();
  }

  openNewProjectDialog(): void {
    const dialogRef = this.dialog.open(ProjectFormComponent, {
      width: '600px',
      disableClose: false,
      autoFocus: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Project created successfully');
      }
    });
  }

  openEditDialog(project: Project): void {
    const dialogRef = this.dialog.open(ProjectFormComponent, {
      width: '600px',
      disableClose: false,
      autoFocus: true,
      data: { project }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Project updated successfully');
      }
    });
  }

  deleteProject(project: Project): void {
    if (confirm(`Are you sure you want to delete "${project.title}"?`)) {
      this.projectState.deleteProject(project.id).subscribe({
        next: () => {
          console.log('Project deleted successfully');
        },
        error: (error) => {
          console.error('Error deleting project:', error);
          alert('Failed to delete project. Please try again.');
        }
      });
    }
  }

  getStatusClass(status: string): string {
    const statusMap: { [key: string]: string } = {
      'ACTIVE': 'status-active',
      'COMPLETED': 'status-completed',
      'DRAFT': 'status-draft'
    };
    return statusMap[status] || '';
  }

  getStatusLabel(status: string): string {
    const labelMap: { [key: string]: string } = {
      'ACTIVE': 'Active',
      'COMPLETED': 'Completed',
      'DRAFT': 'Draft'
    };
    return labelMap[status] || status;
  }

  formatCurrency(amount: number, currency: string): string {
    const symbols: { [key: string]: string } = {
      'EUR': '€',
      'USD': '$',
      'GBP': '£'
    };
    return `${symbols[currency] || currency} ${amount.toLocaleString()}`;
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-GB', {
      day: '2-digit',
      month: 'short',
      year: 'numeric'
    });
  }
}
