import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ILeadAssignment } from 'app/shared/model/lead-assignment.model';
import { getEntities as getLeadAssignments } from 'app/entities/lead-assignment/lead-assignment.reducer';
import { IDispositionSubmission } from 'app/shared/model/disposition-submission.model';
import { getEntities as getDispositionSubmissions } from 'app/entities/disposition-submission/disposition-submission.reducer';
import { getEntity, updateEntity, createEntity, reset } from './call.reducer';
import { ICall } from 'app/shared/model/call.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CallUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const leadAssignments = useAppSelector(state => state.leadAssignment.entities);
  const dispositionSubmissions = useAppSelector(state => state.dispositionSubmission.entities);
  const callEntity = useAppSelector(state => state.call.entity);
  const loading = useAppSelector(state => state.call.loading);
  const updating = useAppSelector(state => state.call.updating);
  const updateSuccess = useAppSelector(state => state.call.updateSuccess);

  const handleClose = () => {
    props.history.push('/call');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getLeadAssignments({}));
    dispatch(getDispositionSubmissions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...callEntity,
      ...values,
      leadAssignment: leadAssignments.find(it => it.id.toString() === values.leadAssignmentId.toString()),
      dispositionSubmission: dispositionSubmissions.find(it => it.id.toString() === values.dispositionSubmissionId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...callEntity,
          status: 'Pending',
          leadAssignmentId: callEntity?.leadAssignment?.id,
          dispositionSubmissionId: callEntity?.dispositionSubmission?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campaignToolApp.call.home.createOrEditLabel" data-cy="CallCreateUpdateHeading">
            <Translate contentKey="campaignToolApp.call.home.createOrEditLabel">Create or edit a Call</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="call-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('campaignToolApp.call.phone')} id="call-phone" name="phone" data-cy="phone" type="text" />
              <ValidatedField
                label={translate('campaignToolApp.call.callDate')}
                id="call-callDate"
                name="callDate"
                data-cy="callDate"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.call.followupDate')}
                id="call-followupDate"
                name="followupDate"
                data-cy="followupDate"
                type="text"
              />
              <ValidatedField label={translate('campaignToolApp.call.notes')} id="call-notes" name="notes" data-cy="notes" type="text" />
              <ValidatedField
                label={translate('campaignToolApp.call.recordingUrl')}
                id="call-recordingUrl"
                name="recordingUrl"
                data-cy="recordingUrl"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.call.status')}
                id="call-status"
                name="status"
                data-cy="status"
                type="select"
              >
                <option value="Pending">{translate('campaignToolApp.CallStatus.Pending')}</option>
                <option value="InProgress">{translate('campaignToolApp.CallStatus.InProgress')}</option>
                <option value="NoAnswer">{translate('campaignToolApp.CallStatus.NoAnswer')}</option>
                <option value="Disconnected">{translate('campaignToolApp.CallStatus.Disconnected')}</option>
                <option value="Answered">{translate('campaignToolApp.CallStatus.Answered')}</option>
                <option value="Interested">{translate('campaignToolApp.CallStatus.Interested')}</option>
                <option value="NotInterested">{translate('campaignToolApp.CallStatus.NotInterested')}</option>
                <option value="FollowupRequested">{translate('campaignToolApp.CallStatus.FollowupRequested')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('campaignToolApp.call.createdBy')}
                id="call-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.call.createdAt')}
                id="call-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.call.updatedBy')}
                id="call-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.call.updatedAt')}
                id="call-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="call-leadAssignment"
                name="leadAssignmentId"
                data-cy="leadAssignment"
                label={translate('campaignToolApp.call.leadAssignment')}
                type="select"
              >
                <option value="" key="0" />
                {leadAssignments
                  ? leadAssignments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="call-dispositionSubmission"
                name="dispositionSubmissionId"
                data-cy="dispositionSubmission"
                label={translate('campaignToolApp.call.dispositionSubmission')}
                type="select"
              >
                <option value="" key="0" />
                {dispositionSubmissions
                  ? dispositionSubmissions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/call" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CallUpdate;
