export interface Project {
  id: string;
  title: string;
  description: string;
  budget: number;
  currency: string;
  startDate: string;
  endDate: string;
  status: ProjectStatus;
  departmentId: string;
  projectManagerId: string;
  createdAt: string;
  createdBy: string;
  updatedAt: string;
  updatedBy: string;
}

export enum ProjectStatus {
  DRAFT = 'DRAFT',
  ACTIVE = 'ACTIVE',
  COMPLETED = 'COMPLETED'
}

export interface CreateProjectRequest {
  title: string;
  description: string;
  budget: number;
  currency: string;
  startDate: string;
  endDate: string;
  departmentId: string;
  projectManagerId: string;
}

export interface ProjectStatistics {
  totalProjects: number;
  activeProjects: number;
  activeProjectsChangePercent: number | null;
  completedProjects: number;
  draftProjects: number;
  totalBudget: number;
  currency: string;
}

export interface ApiResponse<T> {
  status: number;
  message: string;
  data: T;
  requestId: string;
  timestamp: number;
}

export interface PagedResponse<T> {
  content: T[];
  pageable: Pageable;
  totalPages: number;
  totalElements: number;
  last: boolean;
  first: boolean;
  size: number;
  number: number;
  numberOfElements: number;
  empty: boolean;
}

export interface Pageable {
  pageNumber: number;
  pageSize: number;
  offset: number;
  paged: boolean;
  unpaged: boolean;
}
