import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { Match } from '../entities/match';
import { MatchService } from '../services/match.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-match-detail',
  templateUrl: './match-detail.component.html',
  styleUrls: ['./match-detail.component.css']
})
export class MatchDetailComponent implements OnInit {

  private notifier: NotifierService;
  match: Match | undefined;
  formMatch = this.formBuilder.group({
    date: '',
    time: '',
    place: '',
    scoreboard: '',
    status: ''
  });
  data: any;
  matchId = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
  status = ['Em andamento', 'Finalizado']

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
   constructor(notifier: NotifierService,
    private location: Location,
    private route: ActivatedRoute,
    private storage: StorageService,
    private formBuilder: FormBuilder,
    private router: Router,
    private matchService: MatchService) { this.notifier = notifier; }

  ngOnInit(): void {
    this.getMatchById();
  }

  getMatchById(): void{
    try {
      this.matchService.getMatchById(this.matchId).subscribe(res => {
        this.match = res;
        if(this.match.status == 'PROGRESS')
          this.match.status = 'Em andamento'
        else if(this.match.status == 'CLOSED')
          this.match.status = 'Finalizado'

          if(this.match.type == 'GROUP_STAGE')
            this.match.type = 'Fase de grupos'
          else if(this.match.type == 'QUARTER_FINAL')
            this.match.type = 'Quartas de finais'
          else if(this.match.type == 'SEMIFINALS')
            this.match.type = 'Semifinais'
          else if(this.match.type == 'FINAL')
            this.match.type = 'Final'
      },
      () => {
        this.notifier.notify('error', 'Não foi possível carregar a partida no momento, tente novamente mais tarde');
      });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  updateMatch(): void{
    try {
      if(this.formMatch.value.scoreboard || this.formMatch.value.place || this.formMatch.value.time || this.formMatch.value.date || this.formMatch.value.status){
        if(!this.formMatch.value.scoreboard)
          this.formMatch.value.scoreboard = this.match?.scoreboard;

        if(!this.formMatch.value.place)
          this.formMatch.value.place = this.match?.place;

        if(!this.formMatch.value.time)
          this.formMatch.value.time = this.match?.time;

        if(!this.formMatch.value.date)
          this.formMatch.value.date = this.match?.date;
        else{
          const date = this.formMatch.value.date.split("-");
          this.formMatch.value.date = date[2] + "/" + date[1] + "/" + date[0]
        }
          
        if(!this.formMatch.value.status)
          this.formMatch.value.status = this.match?.status == 'Em andamento' ? '0' : '1';
        else{
          if(this.formMatch.value.status == 'Em andamento')
            this.formMatch.value.status = '0';
          else if(this.formMatch.value.status == 'Finalizado')
            this.formMatch.value.status = '1';
          else
            throw new Error('Preencha algum campo');
        }

        this.data = this.formMatch.value;

        this.matchService.updateMatch(this.data, this.matchId).subscribe(() => {
          this.formMatch = this.formBuilder.group({
            date: '',
            time: '',
            place: '',
            scoreboard: '',
            status: ''
          });
          this.notifier.notify('success', 'Campeonato editado com sucesso');
          this.getMatchById();
        }, () => {
          this.formMatch = this.formBuilder.group({
            date: '',
            time: '',
            place: '',
            scoreboard: '',
            status: ''
          });
          this.notifier.notify('error', 'Não foi possível editar a partida no momento, tente novamente mais tarde');
        });
      }else
        this.notifier.notify('error','Preencha algum campo');
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  handleStatusChange(event: any): void {
    this.formMatch.value.status = event.target.value;
  }

  goBack(): void {
    this.location.back();
  }
}
