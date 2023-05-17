import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import AdminService from '@/entities/admin/admin.service';
import { IAdmin } from '@/shared/model/admin.model';

import { IChauffeur, Chauffeur } from '@/shared/model/chauffeur.model';
import ChauffeurService from './chauffeur.service';

const validations: any = {
  chauffeur: {
    firstName: {},
    lastName: {},
    email: {},
    phoneNumber: {},
  },
};

@Component({
  validations,
})
export default class ChauffeurUpdate extends Vue {
  @Inject('chauffeurService') private chauffeurService: () => ChauffeurService;
  @Inject('alertService') private alertService: () => AlertService;

  public chauffeur: IChauffeur = new Chauffeur();

  @Inject('adminService') private adminService: () => AdminService;

  public admins: IAdmin[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.chauffeurId) {
        vm.retrieveChauffeur(to.params.chauffeurId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.chauffeur.id) {
      this.chauffeurService()
        .update(this.chauffeur)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('projetPfeApp.chauffeur.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.chauffeurService()
        .create(this.chauffeur)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('projetPfeApp.chauffeur.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveChauffeur(chauffeurId): void {
    this.chauffeurService()
      .find(chauffeurId)
      .then(res => {
        this.chauffeur = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.adminService()
      .retrieve()
      .then(res => {
        this.admins = res.data;
      });
  }
}
