<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="projetPfeApp.chauffeur.home.createOrEditLabel"
          data-cy="ChauffeurCreateUpdateHeading"
          v-text="$t('projetPfeApp.chauffeur.home.createOrEditLabel')"
        >
          Create or edit a Chauffeur
        </h2>
        <div>
          <div class="form-group" v-if="chauffeur.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="chauffeur.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('projetPfeApp.chauffeur.firstName')" for="chauffeur-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="chauffeur-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.chauffeur.firstName.$invalid, invalid: $v.chauffeur.firstName.$invalid }"
              v-model="$v.chauffeur.firstName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('projetPfeApp.chauffeur.lastName')" for="chauffeur-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="chauffeur-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.chauffeur.lastName.$invalid, invalid: $v.chauffeur.lastName.$invalid }"
              v-model="$v.chauffeur.lastName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('projetPfeApp.chauffeur.email')" for="chauffeur-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="chauffeur-email"
              data-cy="email"
              :class="{ valid: !$v.chauffeur.email.$invalid, invalid: $v.chauffeur.email.$invalid }"
              v-model="$v.chauffeur.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('projetPfeApp.chauffeur.phoneNumber')" for="chauffeur-phoneNumber"
              >Phone Number</label
            >
            <input
              type="text"
              class="form-control"
              name="phoneNumber"
              id="chauffeur-phoneNumber"
              data-cy="phoneNumber"
              :class="{ valid: !$v.chauffeur.phoneNumber.$invalid, invalid: $v.chauffeur.phoneNumber.$invalid }"
              v-model="$v.chauffeur.phoneNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('projetPfeApp.chauffeur.admin')" for="chauffeur-admin">Admin</label>
            <select class="form-control" id="chauffeur-admin" data-cy="admin" name="admin" v-model="chauffeur.admin">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="chauffeur.admin && adminOption.id === chauffeur.admin.id ? chauffeur.admin : adminOption"
                v-for="adminOption in admins"
                :key="adminOption.id"
              >
                {{ adminOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.chauffeur.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./chauffeur-update.component.ts"></script>
