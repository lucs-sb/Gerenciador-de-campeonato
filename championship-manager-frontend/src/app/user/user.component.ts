import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { User } from '../entities/user';
import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  private notifier: NotifierService;
  user: User | any;
  formUser = this.formBuilder.group({
    name: '',
    email: '',
    password: '',
    url_photo: ''
  });
  data: any;

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
   constructor(private userService: UserService, 
    notifier: NotifierService, 
    private formBuilder: FormBuilder, 
    private localStorage: StorageService,
    private router: Router) { this.notifier = notifier; }

  ngOnInit(): void {
    this.getUserById();
  }

  getUserById(): void {
    try {
      this.userService.getUserById(this.localStorage.get("user_id")).subscribe((res) => (this.user = res));
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  updateUser(): void{
    try {
      if(this.formUser.value.name || this.formUser.value.email || this.formUser.value.password || this.formUser.value.url_photo){
        this.data = this.formUser.value;

        this.userService.updateUser(this.data, this.localStorage.get("user_id")).subscribe(() => {
          this.notifier.notify('success', 'Conta editada com sucesso');
          this.formUser = this.formBuilder.group({
            name: '',
            email: '',
            password: '',
            url_photo: ''
          });
          this.getUserById();
        }, () => {
          this.notifier.notify('error', 'Não foi possível criar um novo campeonato no momento, tente novamente mais tarde');
        });
      }else
        this.notifier.notify('error','Preencha algum campo');
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  deleteUser(): void{
    try {
      this.userService.deleteUser(this.localStorage.get("user_id")).subscribe(() => {
        this.router.navigate(['/login']);
      }, () => {
        this.notifier.notify('error', 'Não foi possível deletar sua conta no momento, tente novamente mais tarde');
      });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }
}
