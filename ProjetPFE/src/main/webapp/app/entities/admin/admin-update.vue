<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="projetPfeApp.admin.home.createOrEditLabel"
          data-cy="AdminCreateUpdateHeading"
          v-text="$t('projetPfeApp.admin.home.createOrEditLabel')"
        >
          Create or edit a Admin
        </h2>
        <div>
          <div class="form-group" v-if="admin.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="admin.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('projetPfeApp.admin.roleName')" for="admin-roleName">Role Name</label>
            <input
              type="text"
              class="form-control"
              name="roleName"
              id="admin-roleName"
              data-cy="roleName"
              :class="{ valid: !$v.admin.roleName.$invalid, invalid: $v.admin.roleName.$invalid }"
              v-model="$v.admin.roleName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('projetPfeApp.admin.userName')" for="admin-userName">User Name</label>
            <input
              type="text"
              class="form-control"
              name="userName"
              id="admin-userName"
              data-cy="userName"
              :class="{ valid: !$v.admin.userName.$invalid, invalid: $v.admin.userName.$invalid }"
              v-model="$v.admin.userName.$model"
            />
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
            :disabled="$v.admin.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./admin-update.component.ts"></script>
