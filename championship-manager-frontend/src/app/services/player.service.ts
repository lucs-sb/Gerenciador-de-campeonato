import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pagination } from '../entities/pagination';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private API_URL = 'http://localhost:8080/api/player';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.storage.get('access_token')
    }
  };

  constructor(private http: HttpClient, private storage: StorageService) { }

  getAllPlayersByTeam(team_id: number, pagination: string): Observable<Pagination> {
    {
      return this.http.get<Pagination>(this.API_URL+`/team/${team_id}`+pagination, this.HTTP_OPTIONS)
    }
  }

  addPlayer(player: any) {
    return this.http.post<any>(this.API_URL, player, this.HTTP_OPTIONS); 
  }

  updatePlayer(player: any) {
    return this.http.put<any>(this.API_URL, player, this.HTTP_OPTIONS); 
  }

  deletePlayer(id: number) {
    return this.http.delete<any>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }
}
