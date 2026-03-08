import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'taskDueDate',
  standalone: true
})
export class TaskDueDatePipe implements PipeTransform {

  transform(dueDate: string, status: string): string {

    if(status === 'DONE') return 'done';

    const today = new Date();
    const due = new Date(dueDate);

    today.setHours(0,0,0,0);
    due.setHours(0,0,0,0);

    if(due < today) return 'overdue';

    if(due.getTime() === today.getTime()) return 'today';

    return 'upcoming';
  }

}