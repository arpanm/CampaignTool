import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './field-possible-value.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FieldPossibleValueDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const fieldPossibleValueEntity = useAppSelector(state => state.fieldPossibleValue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldPossibleValueDetailsHeading">
          <Translate contentKey="campaignToolApp.fieldPossibleValue.detail.title">FieldPossibleValue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldPossibleValueEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="campaignToolApp.fieldPossibleValue.value">Value</Translate>
            </span>
          </dt>
          <dd>{fieldPossibleValueEntity.value}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="campaignToolApp.fieldPossibleValue.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{fieldPossibleValueEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.fieldPossibleValue.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{fieldPossibleValueEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.fieldPossibleValue.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {fieldPossibleValueEntity.createdAt ? (
              <TextFormat value={fieldPossibleValueEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.fieldPossibleValue.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{fieldPossibleValueEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.fieldPossibleValue.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {fieldPossibleValueEntity.updatedAt ? (
              <TextFormat value={fieldPossibleValueEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="campaignToolApp.fieldPossibleValue.field">Field</Translate>
          </dt>
          <dd>{fieldPossibleValueEntity.field ? fieldPossibleValueEntity.field.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/field-possible-value" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-possible-value/${fieldPossibleValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldPossibleValueDetail;
