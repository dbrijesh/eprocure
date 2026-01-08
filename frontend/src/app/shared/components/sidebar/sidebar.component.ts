import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';

interface NavItem {
  icon: string;
  label: string;
  route: string;
  tooltip: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule, MatButtonModule, MatTooltipModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  navItems: NavItem[] = [
    { icon: 'dashboard', label: 'Dashboard', route: '/dashboard', tooltip: 'Dashboard' },
    { icon: 'folder', label: 'Projects', route: '/projects', tooltip: 'Projects' },
    { icon: 'notifications', label: 'Notices', route: '/notices', tooltip: 'Procurement Notices' },
    { icon: 'gavel', label: 'Sourcing', route: '/sourcing', tooltip: 'Sourcing Events' },
    { icon: 'business', label: 'Vendors', route: '/vendors', tooltip: 'Vendor Management' },
    { icon: 'description', label: 'Bids', route: '/bids', tooltip: 'Bid Submissions' },
    { icon: 'assignment', label: 'Contracts', route: '/contracts', tooltip: 'Contract Management' },
    { icon: 'bar_chart', label: 'Analytics', route: '/analytics', tooltip: 'Analytics & Reports' },
    { icon: 'settings', label: 'Settings', route: '/settings', tooltip: 'Settings' }
  ];
}
