import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Match } from '../entities/match';
import { Pagination } from '../entities/Pagination';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  private API_URL = 'http://localhost:8080/api/match';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.storage.get('access_token')
    }
  };

  constructor(private http: HttpClient, private storage: StorageService) { }

  getAllMatches(pagination: string, championshipId: number): Observable<Pagination> {
    {
      return this.http.get<Pagination>(this.API_URL+`/championship/${championshipId}`+pagination, this.HTTP_OPTIONS)
    }
  }

  getMatchById(id: number): Observable<Match> {
    {
      return this.http.get<Match>(this.API_URL+`/${id}`, this.HTTP_OPTIONS)
    }
  }

  //getMatchesBySearch(search: string): Observable<Match[]> {
  //  {
  //    return this.http.get<Match[]>(this.API_URL+`/search?search=${search}`, this.HTTP_OPTIONS)
  //  }
  //}

  addMatch(match: any) {
    return this.http.post<any>(this.API_URL, match, this.HTTP_OPTIONS); 
  }

  createKnockoutMatches(championshipId: number, type: string) {
    return this.http.post<any>(this.API_URL+`/championship/${championshipId}/?type=${type}`, this.HTTP_OPTIONS); 
  }

  updateMatch(match: any, id: number) {
    return this.http.patch<any>(this.API_URL+`/${id}`, match, this.HTTP_OPTIONS); 
  }

  deleteMatch(id: number) {
    return this.http.delete<any>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }
}
