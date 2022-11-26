import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { User } from '../entities/user';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private API_URL = 'http://localhost:8080/api/user';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.localStorage.get('access_token')
    }
  };

  private API_URL_LOGIN: string = 'http://localhost:8080/oauth/token';
  private OAUTH_CLIENT: string = 'championship-manager';
  private OAUTH_SECRET: string = 'drhhdrbDRFGHSvds4235!@##$';

  private HTTP_OPTIONS_BASIC = {
    headers: {
      authorization: 'Basic ' + btoa(this.OAUTH_CLIENT + ':' + this.OAUTH_SECRET)
    }
  };

  constructor(private http: HttpClient, private localStorage: StorageService) { }

  login(username: any, password: any): Observable<any>{
    this.localStorage.remove('acsess_token');
    this.localStorage.remove('user_id');
    const body = new FormData();
    body.append('username', username);
    body.append('password', password);
    body.append('grant_type', 'password');

    return this.http.post<any>(this.API_URL_LOGIN, body, this.HTTP_OPTIONS_BASIC)
      .pipe(
        tap(res => {
          this.localStorage.set('access_token', res.access_token);
          this.localStorage.set('user_id', res.user_id);
          this.localStorage.set('isAdmin', res.isAdmin);
        })
      );
  }

  getUserById(id: any) {
    return this.http.get<User>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }

  addUser(user: any) {
    return this.http.post<any>(this.API_URL, user); 
  }

  updateUser(user: any, id: any) {
    return this.http.put<any>(this.API_URL+`/${id}`, user, this.HTTP_OPTIONS); 
  }

  deleteUser(id: any) {
    return this.http.delete<any>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }
}
