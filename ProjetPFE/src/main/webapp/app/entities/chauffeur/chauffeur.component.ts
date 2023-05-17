import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IChauffeur } from '@/shared/model/chauffeur.model';

import ChauffeurService from './chauffeur.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Chauffeur extends Vue {
  @Inject('chauffeurService') private chauffeurService: () => ChauffeurService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public chauffeurs: IChauffeur[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllChauffeurs();
  }

  public clear(): void {
    this.retrieveAllChauffeurs();
  }

  public retrieveAllChauffeurs(): void {
    this.isFetching = true;
    this.chauffeurService()
      .retrieve()
      .then(
        res => {
          this.chauffeurs = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IChauffeur): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeChauffeur(): void {
    this.chauffeurService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('projetPfeApp.chauffeur.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllChauffeurs();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
