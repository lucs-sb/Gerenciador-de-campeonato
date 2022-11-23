import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { Match } from '../entities/match';
import { GroupService } from '../services/group.service';
import { MatchService } from '../services/match.service';
import { StorageService } from '../services/storage.service';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css']
})
export class MatchComponent implements OnInit {

  private notifier: NotifierService;
  matches: Match[] = [];
  home_team = 0;
  away_team = 0;
  championshipId = this.storage.get('lastSeenChampionshipId');
  formMatch = this.formBuilder.group({
    date: '',
    time: '',
    place: '',
    scoreboard: '',
    status: ''
  });
  data: any;
  journey = 'jornada 1';
  pages = ['jornada 1', 'jornada 2', 'jornada 3', 'jornada 4', 'jornada 5', 'jornada 6'];

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
   constructor(notifier: NotifierService,
    private route: ActivatedRoute,
    private storage: StorageService,
    private formBuilder: FormBuilder,
    private router: Router,
    private matchService: MatchService,
    private teamService: TeamService,
    private groupService: GroupService) { this.notifier = notifier; }

  ngOnInit(): void {
    this.getMatchesByChampionship();
  }

  getMatchesByChampionship(): void{
    try {
      this.matchService.getMatchesInGroupStage(this.championshipId, this.journey).subscribe((res) => {
        this.matches = res;

        try {
          this.groupService.getGroups(this.championshipId).subscribe(res => {
            res.forEach(g => {
              g.teams.forEach(t => {
                this.matches.forEach(m => {
                  if(t.id == m.home_team.id)
                    m.name_group = g.name_group
                });
              });
            });
          }, () => {
            this.notifier.notify('error', 'Não foi possível carregar as partidas no momento, tente novamente mais tarde');
            this.router.navigate(['/championship/'+this.championshipId]);
          });
        } catch (ex: any) {
          this.notifier.notify('error', ex);
        }

      },
      () => {
        this.notifier.notify('error', 'Não foi possível carregar as partidas no momento, tente novamente mais tarde');
      });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  handlePageChange(event: any): void {
    this.journey = event.target.value;
    this.getMatchesByChampionship();
  }
}
