import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICall } from 'app/shared/model/call.model';
import { getEntities as getCalls } from 'app/entities/call/call.reducer';
import { IDisposition } from 'app/shared/model/disposition.model';
import { getEntities as getDispositions } from 'app/entities/disposition/disposition.reducer';
import { IDispositionSubmission } from 'app/shared/model/disposition-submission.model';
import { getEntity, updateEntity, createEntity, reset } from './disposition-submission.reducer';

export const DispositionSubmissionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const calls = useAppSelector(state => state.call.entities);
  const dispositions = useAppSelector(state => state.disposition.entities);
  const dispositionSubmissionEntity = useAppSelector(state => state.dispositionSubmission.entity);
  const loading = useAppSelector(state => state.dispositionSubmission.loading);
  const updating = useAppSelector(state => state.dispositionSubmission.updating);
  const updateSuccess = useAppSelector(state => state.dispositionSubmission.updateSuccess);

  const handleClose = () => {
    navigate('/disposition-submission');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCalls({}));
    dispatch(getDispositions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...dispositionSubmissionEntity,
      ...values,
      disposition: dispositions.find(it => it.id.toString() === values.disposition.toString()),
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
          ...dispositionSubmissionEntity,
          disposition: dispositionSubmissionEntity?.disposition?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="automatedPerformanceTestingApp.dispositionSubmission.home.createOrEditLabel"
            data-cy="DispositionSubmissionCreateUpdateHeading"
          >
            <Translate contentKey="automatedPerformanceTestingApp.dispositionSubmission.home.createOrEditLabel">
              Create or edit a DispositionSubmission
            </Translate>
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
                  id="disposition-submission-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.dispositionSubmission.createdBy')}
                id="disposition-submission-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.dispositionSubmission.createdAt')}
                id="disposition-submission-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.dispositionSubmission.updatedBy')}
                id="disposition-submission-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.dispositionSubmission.updatedAt')}
                id="disposition-submission-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="disposition-submission-disposition"
                name="disposition"
                data-cy="disposition"
                label={translate('automatedPerformanceTestingApp.dispositionSubmission.disposition')}
                type="select"
              >
                <option value="" key="0" />
                {dispositions
                  ? dispositions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/disposition-submission" replace color="info">
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

export default DispositionSubmissionUpdate;
