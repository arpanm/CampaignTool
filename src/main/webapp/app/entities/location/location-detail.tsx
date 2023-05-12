import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location.reducer';

export const LocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationEntity = useAppSelector(state => state.location.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.location.detail.title">Location</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{locationEntity.id}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="automatedPerformanceTestingApp.location.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{locationEntity.pincode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="automatedPerformanceTestingApp.location.country">Country</Translate>
            </span>
          </dt>
          <dd>{locationEntity.country}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="automatedPerformanceTestingApp.location.state">State</Translate>
            </span>
          </dt>
          <dd>{locationEntity.state}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="automatedPerformanceTestingApp.location.city">City</Translate>
            </span>
          </dt>
          <dd>{locationEntity.city}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="automatedPerformanceTestingApp.location.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{locationEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.location.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{locationEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.location.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {locationEntity.createdAt ? <TextFormat value={locationEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.location.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{locationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.location.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {locationEntity.updatedAt ? <TextFormat value={locationEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationDetail;
