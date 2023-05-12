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
import { ILead } from 'app/shared/model/lead.model';
import { getEntities as getLeads } from 'app/entities/lead/lead.reducer';
import { ICall } from 'app/shared/model/call.model';
import { getEntities as getCalls } from 'app/entities/call/call.reducer';
import { ILeadAssignment } from 'app/shared/model/lead-assignment.model';
import { getEntity, updateEntity, createEntity, reset } from './lead-assignment.reducer';

export const LeadAssignmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const telecallers = useAppSelector(state => state.telecaller.entities);
  const leads = useAppSelector(state => state.lead.entities);
  const calls = useAppSelector(state => state.call.entities);
  const leadAssignmentEntity = useAppSelector(state => state.leadAssignment.entity);
  const loading = useAppSelector(state => state.leadAssignment.loading);
  const updating = useAppSelector(state => state.leadAssignment.updating);
  const updateSuccess = useAppSelector(state => state.leadAssignment.updateSuccess);

  const handleClose = () => {
    navigate('/lead-assignment');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTelecallers({}));
    dispatch(getLeads({}));
    dispatch(getCalls({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...leadAssignmentEntity,
      ...values,
      telecaller: telecallers.find(it => it.id.toString() === values.telecaller.toString()),
      lead: leads.find(it => it.id.toString() === values.lead.toString()),
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
          ...leadAssignmentEntity,
          telecaller: leadAssignmentEntity?.telecaller?.id,
          lead: leadAssignmentEntity?.lead?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.leadAssignment.home.createOrEditLabel" data-cy="LeadAssignmentCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.leadAssignment.home.createOrEditLabel">
              Create or edit a LeadAssignment
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
                  id="lead-assignment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssignment.assignmentDate')}
                id="lead-assignment-assignmentDate"
                name="assignmentDate"
                data-cy="assignmentDate"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssignment.createdBy')}
                id="lead-assignment-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssignment.createdAt')}
                id="lead-assignment-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssignment.updatedBy')}
                id="lead-assignment-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssignment.updatedAt')}
                id="lead-assignment-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="lead-assignment-telecaller"
                name="telecaller"
                data-cy="telecaller"
                label={translate('automatedPerformanceTestingApp.leadAssignment.telecaller')}
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
                id="lead-assignment-lead"
                name="lead"
                data-cy="lead"
                label={translate('automatedPerformanceTestingApp.leadAssignment.lead')}
                type="select"
              >
                <option value="" key="0" />
                {leads
                  ? leads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lead-assignment" replace color="info">
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

export default LeadAssignmentUpdate;
