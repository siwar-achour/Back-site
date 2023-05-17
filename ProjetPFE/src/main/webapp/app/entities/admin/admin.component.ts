import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdmin } from '@/shared/model/admin.model';

import AdminService from './admin.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Admin extends Vue {
  @Inject('adminService') private adminService: () => AdminService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public admins: IAdmin[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdmins();
  }

  public clear(): void {
    this.retrieveAllAdmins();
  }

  public retrieveAllAdmins(): void {
    this.isFetching = true;
    this.adminService()
      .retrieve()
      .then(
        res => {
          this.admins = res.data;
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

  public prepareRemove(instance: IAdmin): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdmin(): void {
    this.adminService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('projetPfeApp.admin.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAdmins();
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
