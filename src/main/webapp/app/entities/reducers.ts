import client from 'app/entities/client/client.reducer';
import campaign from 'app/entities/campaign/campaign.reducer';
import lead from 'app/entities/lead/lead.reducer';
import attributeKey from 'app/entities/attribute-key/attribute-key.reducer';
import attributePossibleValue from 'app/entities/attribute-possible-value/attribute-possible-value.reducer';
import attribute from 'app/entities/attribute/attribute.reducer';
import location from 'app/entities/location/location.reducer';
import telecaller from 'app/entities/telecaller/telecaller.reducer';
import telecallerInOut from 'app/entities/telecaller-in-out/telecaller-in-out.reducer';
import telecallerAssignment from 'app/entities/telecaller-assignment/telecaller-assignment.reducer';
import leadAssociation from 'app/entities/lead-association/lead-association.reducer';
import leadAssignment from 'app/entities/lead-assignment/lead-assignment.reducer';
import call from 'app/entities/call/call.reducer';
import leadUploadFile from 'app/entities/lead-upload-file/lead-upload-file.reducer';
import disposition from 'app/entities/disposition/disposition.reducer';
import field from 'app/entities/field/field.reducer';
import fieldPossibleValue from 'app/entities/field-possible-value/field-possible-value.reducer';
import dispositionSubmission from 'app/entities/disposition-submission/disposition-submission.reducer';
import dispositionSubmissionValue from 'app/entities/disposition-submission-value/disposition-submission-value.reducer';
import testResult from 'app/entities/test-result/test-result.reducer';
import build from 'app/entities/build/build.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
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
  testResult,
  build,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
