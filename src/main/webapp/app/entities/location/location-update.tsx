import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILead } from 'app/shared/model/lead.model';
import { getEntities as getLeads } from 'app/entities/lead/lead.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntity, updateEntity, createEntity, reset } from './location.reducer';

export const LocationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leads = useAppSelector(state => state.lead.entities);
  const locationEntity = useAppSelector(state => state.location.entity);
  const loading = useAppSelector(state => state.location.loading);
  const updating = useAppSelector(state => state.location.updating);
  const updateSuccess = useAppSelector(state => state.location.updateSuccess);

  const handleClose = () => {
    navigate('/location');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getLeads({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...locationEntity,
      ...values,
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
          ...locationEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.location.home.createOrEditLabel" data-cy="LocationCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.location.home.createOrEditLabel">Create or edit a Location</Translate>
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
                  id="location-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.pincode')}
                id="location-pincode"
                name="pincode"
                data-cy="pincode"
                type="text"
                validate={{
                  min: { value: 10000, message: translate('entity.validation.min', { min: 10000 }) },
                  max: { value: 99999, message: translate('entity.validation.max', { max: 99999 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.country')}
                id="location-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.state')}
                id="location-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.city')}
                id="location-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.isActive')}
                id="location-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.createdBy')}
                id="location-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.createdAt')}
                id="location-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.updatedBy')}
                id="location-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.location.updatedAt')}
                id="location-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/location" replace color="info">
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

export default LocationUpdate;
