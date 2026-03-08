export interface Task {
priority: any;
assignedTo: any;
  id: number;
  title: string;
  description: string;
  status: string;
  dueDate?: string;
}