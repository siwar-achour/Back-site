import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdmin } from '@/shared/model/admin.model';
import AdminService from './admin.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AdminDetails extends Vue {
  @Inject('adminService') private adminService: () => AdminService;
  @Inject('alertService') private alertService: () => AlertService;

  public admin: IAdmin = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adminId) {
        vm.retrieveAdmin(to.params.adminId);
      }
    });
  }

  public retrieveAdmin(adminId) {
    this.adminService()
      .find(adminId)
      .then(res => {
        this.admin = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
