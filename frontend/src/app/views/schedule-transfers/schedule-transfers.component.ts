import { Component } from '@angular/core';
import { TransferService } from '../../transfer.service';
import { ToastrService } from 'ngx-toastr';
import {
  AbstractControl,
  AbstractControlOptions,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-schedule-transfers',
  templateUrl: './schedule-transfers.component.html',
  styleUrls: ['./schedule-transfers.component.css'],
})
export class ScheduleTransfersComponent {
  transferForm: FormGroup;
  transferDate: Date = new Date();
  transferAmount: string = '';

  constructor(
    private fb: FormBuilder,
    private transferService: TransferService,
    private toast: ToastrService,
    private router: Router
  ) {
    const formOptions: AbstractControlOptions = {
      validators: [this.accountValidator, this.dateValidator],
    };
    this.transferForm = this.fb.group(
      {
        originAccount: [
          '',
          [Validators.required, Validators.pattern(/^\d{10}$/)],
        ],
        destinationAccount: [
          '',
          [Validators.required, Validators.pattern(/^\d{10}$/)],
        ],
        transferAmount: [
          '',
          [
            Validators.required,
            Validators.min(0),
            Validators.pattern(/^\d+(\.\d{1,2})?$/),
          ],
        ],

        transferDate: [new Date(), [Validators.required]],
      },
      formOptions
    );
  }

  accountValidator(
    control: AbstractControl
  ): { [key: string]: boolean } | null {
    const originAccount = control.get('originAccount')?.value;
    const destinationAccount = control.get('destinationAccount')?.value;

    if (
      originAccount &&
      destinationAccount &&
      originAccount === destinationAccount
    ) {
      return { sameAccount: true };
    }

    return null;
  }
  dateValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const transferDate = new Date(control.value);
    const currentDate = new Date();
    const maxAllowedDate = new Date();
    maxAllowedDate.setDate(currentDate.getDate() + 49);

    if (transferDate < currentDate || transferDate > maxAllowedDate) {
      return { invalidDate: true };
    }

    return null;
  }

  showToast(message: string, type: 'success' | 'info' | 'warning' | 'error') {
    this.toast.show(message, '', {
      closeButton: true,
      timeOut: 5000,
      positionClass: 'toast-top-right',
      enableHtml: true,
      progressBar: true,
      toastClass: `ngx-toastr ${type}`,
    });
  }

  scheduleTransfer(): void {
    if (this.transferForm.valid) {
      const transferData = this.transferForm.value;

      this.transferService.scheduleTransfer(transferData).subscribe({
        next: (response) => {
          const apiMessage = response.message;

          this.showToast(apiMessage, 'success');
          this.router.navigate(['/']);
        },
        error: (error) => {
          let errorMessage = '';

          if (error && error.error && error.error.error) {
            errorMessage = error.error.error;
          }
          this.showToast(errorMessage, 'error');
        },
      });
    } else {
      this.showToast('Formulário inválido. Verifique os campos.', 'warning');
    }
  }
}
