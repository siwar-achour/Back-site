/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AdminDetailComponent from '@/entities/admin/admin-details.vue';
import AdminClass from '@/entities/admin/admin-details.component';
import AdminService from '@/entities/admin/admin.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Admin Management Detail Component', () => {
    let wrapper: Wrapper<AdminClass>;
    let comp: AdminClass;
    let adminServiceStub: SinonStubbedInstance<AdminService>;

    beforeEach(() => {
      adminServiceStub = sinon.createStubInstance<AdminService>(AdminService);

      wrapper = shallowMount<AdminClass>(AdminDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { adminService: () => adminServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAdmin = { id: 'ABC' };
        adminServiceStub.find.resolves(foundAdmin);

        // WHEN
        comp.retrieveAdmin('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.admin).toBe(foundAdmin);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAdmin = { id: 'ABC' };
        adminServiceStub.find.resolves(foundAdmin);

        // WHEN
        comp.beforeRouteEnter({ params: { adminId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.admin).toBe(foundAdmin);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
