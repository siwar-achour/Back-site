<template>
  <div>
    <h2 id="page-heading" data-cy="ChauffeurHeading">
      <span v-text="$t('projetPfeApp.chauffeur.home.title')" id="chauffeur-heading">Chauffeurs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('projetPfeApp.chauffeur.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ChauffeurCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-chauffeur"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('projetPfeApp.chauffeur.home.createLabel')"> Create a new Chauffeur </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && chauffeurs && chauffeurs.length === 0">
      <span v-text="$t('projetPfeApp.chauffeur.home.notFound')">No chauffeurs found</span>
    </div>
    <div class="table-responsive" v-if="chauffeurs && chauffeurs.length > 0">
      <table class="table table-striped" aria-describedby="chauffeurs">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('projetPfeApp.chauffeur.firstName')">First Name</span></th>
            <th scope="row"><span v-text="$t('projetPfeApp.chauffeur.lastName')">Last Name</span></th>
            <th scope="row"><span v-text="$t('projetPfeApp.chauffeur.email')">Email</span></th>
            <th scope="row"><span v-text="$t('projetPfeApp.chauffeur.phoneNumber')">Phone Number</span></th>
            <th scope="row"><span v-text="$t('projetPfeApp.chauffeur.admin')">Admin</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="chauffeur in chauffeurs" :key="chauffeur.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ChauffeurView', params: { chauffeurId: chauffeur.id } }">{{ chauffeur.id }}</router-link>
            </td>
            <td>{{ chauffeur.firstName }}</td>
            <td>{{ chauffeur.lastName }}</td>
            <td>{{ chauffeur.email }}</td>
            <td>{{ chauffeur.phoneNumber }}</td>
            <td>
              <div v-if="chauffeur.admin">
                <router-link :to="{ name: 'AdminView', params: { adminId: chauffeur.admin.id } }">{{ chauffeur.admin.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ChauffeurView', params: { chauffeurId: chauffeur.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ChauffeurEdit', params: { chauffeurId: chauffeur.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(chauffeur)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="projetPfeApp.chauffeur.delete.question" data-cy="chauffeurDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-chauffeur-heading" v-text="$t('projetPfeApp.chauffeur.delete.question', { id: removeId })">
          Are you sure you want to delete this Chauffeur?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-chauffeur"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeChauffeur()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./chauffeur.component.ts"></script>
