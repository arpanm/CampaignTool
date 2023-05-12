import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeadAssignment } from 'app/shared/model/lead-assignment.model';
import { getEntities as getLeadAssignments } from 'app/entities/lead-assignment/lead-assignment.reducer';
import { IDispositionSubmission } from 'app/shared/model/disposition-submission.model';
import { getEntities as getDispositionSubmissions } from 'app/entities/disposition-submission/disposition-submission.reducer';
import { ICall } from 'app/shared/model/call.model';
import { CallStatus } from 'app/shared/model/enumerations/call-status.model';
import { getEntity, updateEntity, createEntity, reset } from './call.reducer';

export const CallUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leadAssignments = useAppSelector(state => state.leadAssignment.entities);
  const dispositionSubmissions = useAppSelector(state => state.dispositionSubmission.entities);
  const callEntity = useAppSelector(state => state.call.entity);
  const loading = useAppSelector(state => state.call.loading);
  const updating = useAppSelector(state => state.call.updating);
  const updateSuccess = useAppSelector(state => state.call.updateSuccess);
  const callStatusValues = Object.keys(CallStatus);

  const handleClose = () => {
    navigate('/call');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
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
      leadAssignment: leadAssignments.find(it => it.id.toString() === values.leadAssignment.toString()),
      dispositionSubmission: dispositionSubmissions.find(it => it.id.toString() === values.dispositionSubmission.toString()),
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
          status: 'Pending',
          ...callEntity,
          leadAssignment: callEntity?.leadAssignment?.id,
          dispositionSubmission: callEntity?.dispositionSubmission?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.call.home.createOrEditLabel" data-cy="CallCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.call.home.createOrEditLabel">Create or edit a Call</Translate>
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
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.phone')}
                id="call-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.callDate')}
                id="call-callDate"
                name="callDate"
                data-cy="callDate"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.followupDate')}
                id="call-followupDate"
                name="followupDate"
                data-cy="followupDate"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.notes')}
                id="call-notes"
                name="notes"
                data-cy="notes"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.recordingUrl')}
                id="call-recordingUrl"
                name="recordingUrl"
                data-cy="recordingUrl"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.status')}
                id="call-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {callStatusValues.map(callStatus => (
                  <option value={callStatus} key={callStatus}>
                    {translate('automatedPerformanceTestingApp.CallStatus.' + callStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.createdBy')}
                id="call-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.createdAt')}
                id="call-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.updatedBy')}
                id="call-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.call.updatedAt')}
                id="call-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="call-leadAssignment"
                name="leadAssignment"
                data-cy="leadAssignment"
                label={translate('automatedPerformanceTestingApp.call.leadAssignment')}
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
                name="dispositionSubmission"
                data-cy="dispositionSubmission"
                label={translate('automatedPerformanceTestingApp.call.dispositionSubmission')}
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
