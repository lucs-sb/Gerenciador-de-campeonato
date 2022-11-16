import { Component, OnInit } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { StorageService } from '../services/storage.service';
import { Championship } from '../entities/championship';
import { ChampionshipService } from '../services/championship.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private notifier: NotifierService;
  championships: Championship[] = [];
  search: string = '';
  ordination: string = 'DESC';

  formChampionship = this.formBuilder.group({
    name: ['', [Validators.required]],
    description: ['', [Validators.required]],
    number_of_teams: [0, [Validators.required]],
    award: ['', [Validators.required]],
    user: this.formBuilder.group({
      id: this.localStorage.get('user_id')
    }),
    teams: new FormBuilder().array([
      new FormGroup({
        id: new FormControl()
      })
    ])
  });

  data: any;

  currentIndex = -1;
  page = 0;
  count = 0;
  pageSize = 10;
  pageSizes = [10, 15, 20];

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private championshipService: ChampionshipService, 
    notifier: NotifierService, 
    private formBuilder: FormBuilder, 
    private localStorage: StorageService,
    private router: Router) { this.notifier = notifier; }

  ngOnInit(): void {
    this.retrieveChampionships();
  }

  searchChampionships(){
    try {
      this.championships = [];
      this.championshipService.getChampionshipsBySearch(this.search, this.ordination).subscribe((championships) => (this.championships = championships));
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  addChampionship(): void{
    try {
      if(!this.formChampionship.value.name || !this.formChampionship.value.description || !this.formChampionship.value.number_of_teams || !this.formChampionship.value.award || !this.formChampionship.value.teams)
        throw new Error('Preencha todos os campos');

      this.data = this.formChampionship.value;
      this.championshipService.addChampionship(this.data).subscribe(() => {
        this.notifier.notify('success', 'Campeonato criado com sucesso');
        //this.router.navigate(['/home']);
      }, () => {
        this.notifier.notify('error', 'Não foi possível criar um novo campeonato no momento, tente novamente mais tarde');
      });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveChampionships();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.retrieveChampionships();
  }

  retrieveChampionships(): void{
    try {
      this.championshipService.getAllChampionships("?size="+this.pageSize+"&page="+this.page+"&sort="+this.ordination)
      .subscribe(
        res => {
          this.championships = res.content;
          this.page = res.number;
          this.count = res.totalElements;
          this.pageSize = res.size;
        },
        error => {
          console.log(error);
        });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }
}
