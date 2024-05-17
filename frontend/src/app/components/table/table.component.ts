import { Component } from '@angular/core';
import { TransferService } from '../../transfer.service';
import { format, addHours } from 'date-fns';
@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent {
  transferData: any[] = [];

  constructor(private transferDataService: TransferService) {}

  ngOnInit(): void {
    this.loadTransferData();
  }

  formatDate(data: string): string {
    const dataComFusoHorario = addHours(
      new Date(data),
      new Date().getTimezoneOffset() / 60
    );
    return format(dataComFusoHorario, 'dd/MM/yyyy');
  }

  loadTransferData(): void {
    this.transferDataService.getTransferData(this.transferData).subscribe({
      next: (data) => {
        this.transferData = data;
      },
      error: (error) => {
        console.error('Erro ao obter dados da API', error);
      },
    });
  }
}
