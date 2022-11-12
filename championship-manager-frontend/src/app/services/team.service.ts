import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pagination } from '../entities/pagination';
import { Team } from '../entities/team';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private API_URL = 'http://localhost:8080/api/team';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.storage.get('access_token')
    }
  };

  constructor(private http: HttpClient, private storage: StorageService) { }

  getAllTeams(pagination: string): Observable<Pagination> {
    {
      return this.http.get<Pagination>(this.API_URL+`/user/${this.storage.get('user_id')}`+pagination, this.HTTP_OPTIONS)
    }
  }

  getTeamById(id: number): Observable<Team> {
    {
      return this.http.get<Team>(this.API_URL+`/${id}`, this.HTTP_OPTIONS)
    }
  }

  getTeamsBySearch(search: string): Observable<Team[]> {
    {
      return this.http.get<Team[]>(this.API_URL+`/user/${this.storage.get('user_id')}/search?search=${search}`, this.HTTP_OPTIONS)
    }
  }

  addTeam(team: any) {
    return this.http.post<any>(this.API_URL+`/user/${this.storage.get('user_id')}`, team, this.HTTP_OPTIONS); 
  }

  updateTeam(team: any, id: number) {
    return this.http.put<any>(this.API_URL+`/${id}`, team, this.HTTP_OPTIONS); 
  }

  deleteTeam(id: number) {
    return this.http.delete<any>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }
}
