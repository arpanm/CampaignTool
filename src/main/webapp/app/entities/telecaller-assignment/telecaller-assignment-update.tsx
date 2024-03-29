import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITelecaller } from 'app/shared/model/telecaller.model';
import { getEntities as getTelecallers } from 'app/entities/telecaller/telecaller.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities as getCampaigns } from 'app/entities/campaign/campaign.reducer';
import { ITelecallerAssignment } from 'app/shared/model/telecaller-assignment.model';
import { getEntity, updateEntity, createEntity, reset } from './telecaller-assignment.reducer';

export const TelecallerAssignmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const telecallers = useAppSelector(state => state.telecaller.entities);
  const campaigns = useAppSelector(state => state.campaign.entities);
  const telecallerAssignmentEntity = useAppSelector(state => state.telecallerAssignment.entity);
  const loading = useAppSelector(state => state.telecallerAssignment.loading);
  const updating = useAppSelector(state => state.telecallerAssignment.updating);
  const updateSuccess = useAppSelector(state => state.telecallerAssignment.updateSuccess);

  const handleClose = () => {
    navigate('/telecaller-assignment');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTelecallers({}));
    dispatch(getCampaigns({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...telecallerAssignmentEntity,
      ...values,
      telecaller: telecallers.find(it => it.id.toString() === values.telecaller.toString()),
      campaign: campaigns.find(it => it.id.toString() === values.campaign.toString()),
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
          ...telecallerAssignmentEntity,
          telecaller: telecallerAssignmentEntity?.telecaller?.id,
          campaign: telecallerAssignmentEntity?.campaign?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="automatedPerformanceTestingApp.telecallerAssignment.home.createOrEditLabel"
            data-cy="TelecallerAssignmentCreateUpdateHeading"
          >
            <Translate contentKey="automatedPerformanceTestingApp.telecallerAssignment.home.createOrEditLabel">
              Create or edit a TelecallerAssignment
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
                  id="telecaller-assignment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.telecallerAssignment.assignmentDate')}
                id="telecaller-assignment-assignmentDate"
                name="assignmentDate"
                data-cy="assignmentDate"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.telecallerAssignment.createdBy')}
                id="telecaller-assignment-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.telecallerAssignment.createdAt')}
                id="telecaller-assignment-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.telecallerAssignment.updatedBy')}
                id="telecaller-assignment-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.telecallerAssignment.updatedAt')}
                id="telecaller-assignment-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="telecaller-assignment-telecaller"
                name="telecaller"
                data-cy="telecaller"
                label={translate('automatedPerformanceTestingApp.telecallerAssignment.telecaller')}
                type="select"
              >
                <option value="" key="0" />
                {telecallers
                  ? telecallers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="telecaller-assignment-campaign"
                name="campaign"
                data-cy="campaign"
                label={translate('automatedPerformanceTestingApp.telecallerAssignment.campaign')}
                type="select"
              >
                <option value="" key="0" />
                {campaigns
                  ? campaigns.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/telecaller-assignment" replace color="info">
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

export default TelecallerAssignmentUpdate;
