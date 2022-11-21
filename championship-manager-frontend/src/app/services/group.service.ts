import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Group } from '../entities/group';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private API_URL = 'http://localhost:8080/api/group';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.storage.get('access_token')
    }
  };

  constructor(private http: HttpClient, private storage: StorageService) { }

  getGroups(championshipId: any): Observable<Group[]> {
    {
      return this.http.get<Group[]>(this.API_URL+`/championship/${championshipId}`, this.HTTP_OPTIONS)
    }
  }

  updateGroup(group: any) {
    return this.http.put<any>(this.API_URL, group, this.HTTP_OPTIONS); 
  }
}
