import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  Project,
  CreateProjectRequest,
  ProjectStatistics,
  ApiResponse,
  PagedResponse,
  ProjectStatus
} from '../models/project.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectApiService {
  private apiUrl = `${environment.apiUrl}/v1/projects`;

  constructor(private http: HttpClient) {}

  listProjects(page = 0, size = 10, status?: ProjectStatus): Observable<ApiResponse<PagedResponse<Project>>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (status) {
      params = params.set('status', status);
    }

    return this.http.get<ApiResponse<PagedResponse<Project>>>(this.apiUrl, { params });
  }

  getProject(id: string): Observable<ApiResponse<Project>> {
    return this.http.get<ApiResponse<Project>>(`${this.apiUrl}/${id}`);
  }

  createProject(request: CreateProjectRequest): Observable<ApiResponse<Project>> {
    return this.http.post<ApiResponse<Project>>(this.apiUrl, request);
  }

  updateProject(id: string, request: CreateProjectRequest): Observable<ApiResponse<Project>> {
    return this.http.put<ApiResponse<Project>>(`${this.apiUrl}/${id}`, request);
  }

  deleteProject(id: string): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`);
  }

  getStatistics(): Observable<ApiResponse<ProjectStatistics>> {
    return this.http.get<ApiResponse<ProjectStatistics>>(`${this.apiUrl}/statistics`);
  }
}
