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
  journey = 'Jornada 1';
  pages = ['Jornada 1', 'Jornada 2', 'Jornada 3', 'Jornada 4', 'Jornada 5', 'Jornada 6'];
  isGroup = false;
  isQuarterFinal = false;
  isSemifinal = false;
  isFinal = false;
  href = '';

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
    this.href = this.router.url.split('/')[2];
    if(this.href == 'group-stage'){
      this.isGroup = true;
      this.getMatchesInGroupStage();
    } else if(this.href == 'quarter-final'){
      this.isQuarterFinal = true;
      this.getMatchesInQuarterFinal();
    } else if(this.href == 'semifinals'){
      this.isSemifinal = true;
      this.getMatchesInSemifinals();
    } else if(this.href == 'final'){
      this.isFinal = true;
      this.getMatchesInFinal();
    }
  }

  getMatchesInGroupStage(): void{
    try {
      this.matchService.getMatchesByParams(this.championshipId, this.journey, 0).subscribe((res) => {
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

  getMatchesInQuarterFinal(): void{
    try {
      this.matchService.getMatchesByParams(this.championshipId, '', 1).subscribe((res) => {
        this.matches = res;
      },
      () => {
        this.notifier.notify('error', 'Não foi possível carregar as partidas no momento, tente novamente mais tarde');
      });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  getMatchesInSemifinals(): void{
    try {
      this.matchService.getMatchesByParams(this.championshipId, '', 2).subscribe((res) => {
        this.matches = res;
      },
      () => {
        this.notifier.notify('error', 'Não foi possível carregar as partidas no momento, tente novamente mais tarde');
      });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  getMatchesInFinal(): void{
    try {
      this.matchService.getMatchesByParams(this.championshipId, '', 3).subscribe((res) => {
        this.matches = res;
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
    this.getMatchesInGroupStage();
  }

  createMatches(number: number): void{
    try {
      this.matchService.createKnockoutMatches(this.championshipId, number).subscribe(() => {
        this.notifier.notify('success', 'Partidas das quartas de finais criada com sucesso');
      },
      (res) => {
        this.notifier.notify('error', res.error.message);
      });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }
}
