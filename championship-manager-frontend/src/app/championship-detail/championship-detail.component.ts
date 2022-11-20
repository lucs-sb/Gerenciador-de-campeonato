import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { Championship } from '../entities/championship';
import { ChampionshipService } from '../services/championship.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-championship-detail',
  templateUrl: './championship-detail.component.html',
  styleUrls: ['./championship-detail.component.css']
})
export class ChampionshipDetailComponent implements OnInit {
  private notifier: NotifierService;
  championship: Championship | undefined;
  formChampionship = this.formBuilder.group({
    name: '',
    description: '',
    award: '',
    status: ''
  });
  data: any;
  championship_id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
  status = ['Em andamento', 'Finalizado']

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
    private championshipService: ChampionshipService) { this.notifier = notifier; }

  ngOnInit(): void {
    this.getChampionshipById();
  }

  getChampionshipById(): void{
    try {
      this.championshipService.getChampionshipById(this.championship_id)
      .subscribe(
        (res) => {
          this.championship = res;
          this.storage.set('lastSeenChampionshipId', this.championship.id.toString());
          if(this.championship.status == 'IN_PROGRESS')
            this.championship.status = 'Em andamento'
          else if(this.championship.status == 'CLOSED')
            this.championship.status = 'Finalizado'
        }, () => {
          this.notifier.notify('error', 'Não foi possível carregar o time no momento, tente novamente mais tarde');
        });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  updateChampionship(): void{
    try {
      if(this.formChampionship.value.name || this.formChampionship.value.description || this.formChampionship.value.award || this.formChampionship.value.status){
        if(!this.formChampionship.value.name)
          this.formChampionship.value.name = this.championship?.name;
        if(!this.formChampionship.value.description)
          this.formChampionship.value.description = this.championship?.description;
        if(!this.formChampionship.value.award)
          this.formChampionship.value.award = this.championship?.award;
        if(!this.formChampionship.value.status)
          this.formChampionship.value.status = this.championship?.status == 'Em andamento' ? '0' : '1';
        else{
          if(this.formChampionship.value.status == 'Em andamento')
            this.formChampionship.value.status = '0';
          else if(this.formChampionship.value.status == 'Finalizado')
            this.formChampionship.value.status = '1';
          else
            throw new Error('Preencha algum campo');
        }

        this.data = this.formChampionship.value;

        this.championshipService.updateChampionship(this.data, this.championship_id).subscribe(() => {
          this.notifier.notify('success', 'Campeonato editado com sucesso');
          this.formChampionship = this.formBuilder.group({
            name: '',
            description: '',
            award: '',
            status: ''
          });
          this.getChampionshipById();
        }, () => {
          this.notifier.notify('error', 'Não foi possível editar o campeonato no momento, tente novamente mais tarde');
        });
      }else
        this.notifier.notify('error','Preencha algum campo');
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  deleteChampionship(): void{
    try {
      this.championshipService.deleteChampionship(this.championship_id)
      .subscribe(
        () => (this.router.navigate(['/home'])), () => {
          this.notifier.notify('error', 'Não foi possível deletar o campeonato no momento, tente novamente mais tarde');
        });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  handleStatusChange(event: any): void {
    this.formChampionship.value.status = event.target.value;
  }
}
