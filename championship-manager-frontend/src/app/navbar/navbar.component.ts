import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  name: string = '';
  url_photo: string = '';

  constructor(private storage: StorageService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {

  }

  getUserById(): void{
    this.userService.getUserById(localStorage.getItem("user_id")).subscribe((req) => {
      this.name = req.name;
      this.url_photo = req.url_photo;
    }, () => {
      this.storage.logoutUser();
      this.router.navigate(['/']);
    });
  }

  logout() {
    this.storage.clear();
  }
}
