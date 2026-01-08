import { Injectable, signal, computed } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { ProjectApiService } from './project-api.service';
import { Project, ProjectStatistics, ProjectStatus, CreateProjectRequest, ApiResponse } from '../models/project.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectStateService {
  private projectsSignal = signal<Project[]>([]);
  private statisticsSignal = signal<ProjectStatistics | null>(null);
  private loadingSignal = signal<boolean>(false);
  private errorSignal = signal<string | null>(null);

  projects = this.projectsSignal.asReadonly();
  statistics = this.statisticsSignal.asReadonly();
  loading = this.loadingSignal.asReadonly();
  error = this.errorSignal.asReadonly();

  recentProjects = computed(() => this.projectsSignal().slice(0, 5));

  constructor(private apiService: ProjectApiService) {}

  loadProjects(page = 0, size = 10, status?: ProjectStatus): void {
    this.loadingSignal.set(true);
    this.errorSignal.set(null);

    this.apiService.listProjects(page, size, status).subscribe({
      next: (response) => {
        this.projectsSignal.set(response.data.content);
        this.loadingSignal.set(false);
      },
      error: (err) => {
        this.errorSignal.set(err.message || 'Failed to load projects');
        this.loadingSignal.set(false);
      }
    });
  }

  loadStatistics(): void {
    this.apiService.getStatistics().subscribe({
      next: (response) => {
        this.statisticsSignal.set(response.data);
      },
      error: (err) => {
        this.errorSignal.set(err.message || 'Failed to load statistics');
      }
    });
  }

  createProject(request: CreateProjectRequest): Observable<ApiResponse<Project>> {
    return this.apiService.createProject(request).pipe(
      tap(() => {
        // Refresh data after successful creation
        this.refreshData();
      })
    );
  }

  refreshData(): void {
    this.loadStatistics();
    this.loadProjects();
  }
}
