-- Sample data for H2 database (development only)

INSERT INTO projects (id, title, description, budget, currency, start_date, end_date, status, department_id, project_manager_id, created_at, created_by, updated_at, updated_by)
VALUES
    (RANDOM_UUID(), 'IT Infrastructure Upgrade', 'Upgrade company-wide IT infrastructure including servers, networking equipment, and workstations', 250000.00, 'EUR', '2024-01-15', '2024-12-31', 'ACTIVE', RANDOM_UUID(), RANDOM_UUID(), CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    (RANDOM_UUID(), 'Office Furniture Procurement', 'Purchase ergonomic office furniture for new headquarters building', 75000.00, 'EUR', '2024-02-01', '2024-04-30', 'COMPLETED', RANDOM_UUID(), RANDOM_UUID(), CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    (RANDOM_UUID(), 'Fleet Vehicle Acquisition', 'Acquire 20 electric vehicles for company fleet replacement program', 450000.00, 'EUR', '2024-03-01', '2024-09-30', 'ACTIVE', RANDOM_UUID(), RANDOM_UUID(), CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    (RANDOM_UUID(), 'Software Licenses Renewal', 'Renew enterprise software licenses for productivity and development tools', 125000.00, 'EUR', '2024-01-01', '2024-01-31', 'COMPLETED', RANDOM_UUID(), RANDOM_UUID(), CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
    (RANDOM_UUID(), 'Marketing Materials Procurement', 'Design and print marketing materials for Q2 2024 campaigns', 35000.00, 'EUR', '2024-04-01', '2024-06-30', 'DRAFT', RANDOM_UUID(), RANDOM_UUID(), CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');
