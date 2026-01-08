import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { ProjectStateService } from '../../core/services/project-state.service';
import { ProjectFormComponent } from '../projects/components/project-form/project-form.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  private projectState = inject(ProjectStateService);
  private dialog = inject(MatDialog);

  statistics = this.projectState.statistics;
  recentProjects = this.projectState.recentProjects;
  loading = this.projectState.loading;

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
}
