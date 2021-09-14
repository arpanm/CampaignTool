import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import client from 'app/entities/client/client.reducer';
// prettier-ignore
import campaign from 'app/entities/campaign/campaign.reducer';
// prettier-ignore
import lead from 'app/entities/lead/lead.reducer';
// prettier-ignore
import attributeKey from 'app/entities/attribute-key/attribute-key.reducer';
// prettier-ignore
import attributePossibleValue from 'app/entities/attribute-possible-value/attribute-possible-value.reducer';
// prettier-ignore
import attribute from 'app/entities/attribute/attribute.reducer';
// prettier-ignore
import location from 'app/entities/location/location.reducer';
// prettier-ignore
import telecaller from 'app/entities/telecaller/telecaller.reducer';
// prettier-ignore
import telecallerInOut from 'app/entities/telecaller-in-out/telecaller-in-out.reducer';
// prettier-ignore
import telecallerAssignment from 'app/entities/telecaller-assignment/telecaller-assignment.reducer';
// prettier-ignore
import leadAssociation from 'app/entities/lead-association/lead-association.reducer';
// prettier-ignore
import leadAssignment from 'app/entities/lead-assignment/lead-assignment.reducer';
// prettier-ignore
import call from 'app/entities/call/call.reducer';
// prettier-ignore
import leadUploadFile from 'app/entities/lead-upload-file/lead-upload-file.reducer';
// prettier-ignore
import disposition from 'app/entities/disposition/disposition.reducer';
// prettier-ignore
import field from 'app/entities/field/field.reducer';
// prettier-ignore
import fieldPossibleValue from 'app/entities/field-possible-value/field-possible-value.reducer';
// prettier-ignore
import dispositionSubmission from 'app/entities/disposition-submission/disposition-submission.reducer';
// prettier-ignore
import dispositionSubmissionValue from 'app/entities/disposition-submission-value/disposition-submission-value.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  client,
  campaign,
  lead,
  attributeKey,
  attributePossibleValue,
  attribute,
  location,
  telecaller,
  telecallerInOut,
  telecallerAssignment,
  leadAssociation,
  leadAssignment,
  call,
  leadUploadFile,
  disposition,
  field,
  fieldPossibleValue,
  dispositionSubmission,
  dispositionSubmissionValue,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
