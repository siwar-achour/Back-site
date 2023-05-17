import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import ChauffeurService from '@/entities/chauffeur/chauffeur.service';
import { IChauffeur } from '@/shared/model/chauffeur.model';

import ClientService from '@/entities/client/client.service';
import { IClient } from '@/shared/model/client.model';

import { IAdmin, Admin } from '@/shared/model/admin.model';
import AdminService from './admin.service';

const validations: any = {
  admin: {
    roleName: {},
    userName: {},
  },
};

@Component({
  validations,
})
export default class AdminUpdate extends Vue {
  @Inject('adminService') private adminService: () => AdminService;
  @Inject('alertService') private alertService: () => AlertService;

  public admin: IAdmin = new Admin();

  @Inject('chauffeurService') private chauffeurService: () => ChauffeurService;

  public chauffeurs: IChauffeur[] = [];

  @Inject('clientService') private clientService: () => ClientService;

  public clients: IClient[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adminId) {
        vm.retrieveAdmin(to.params.adminId);
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
    if (this.admin.id) {
      this.adminService()
        .update(this.admin)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('projetPfeApp.admin.updated', { param: param.id });
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
      this.adminService()
        .create(this.admin)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('projetPfeApp.admin.created', { param: param.id });
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

  public retrieveAdmin(adminId): void {
    this.adminService()
      .find(adminId)
      .then(res => {
        this.admin = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.chauffeurService()
      .retrieve()
      .then(res => {
        this.chauffeurs = res.data;
      });
    this.clientService()
      .retrieve()
      .then(res => {
        this.clients = res.data;
      });
  }
}
