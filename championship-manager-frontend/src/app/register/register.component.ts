import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private notifier: NotifierService;
  
  formUser = this.formBuilder.group({
    name: ['', [Validators.required]],
    email: ['', [Validators.email]],
    password: ['', [Validators.required]],
    url_photo: ''
  });

  data: any;

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private registerService: UserService, 
    private formBuilder: FormBuilder,
    private storage: StorageService, notifier: NotifierService,
    private router: Router) { this.notifier = notifier; }

  ngOnInit(): void {
  }

  register(): void{
    try {
      this.data = this.formUser.value;
      this.registerService.addUser(this.data).subscribe(() => {
        this.storage.logoutUser();
        this.router.navigate(['/']);
      }, () => {
        this.storage.logoutUser();
        this.notifier.notify('error', 'Não foi possível realizar o cadastro no momento, tente novamente mais tarde');
      });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  redirect(): void{
    this.router.navigate(['/']);
  }
}