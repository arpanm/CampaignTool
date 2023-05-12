import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './telecaller-in-out.reducer';

export const TelecallerInOutDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const telecallerInOutEntity = useAppSelector(state => state.telecallerInOut.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="telecallerInOutDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.detail.title">TelecallerInOut</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{telecallerInOutEntity.id}</dd>
          <dt>
            <span id="eventType">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.eventType">Event Type</Translate>
            </span>
          </dt>
          <dd>{telecallerInOutEntity.eventType}</dd>
          <dt>
            <span id="eventTime">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.eventTime">Event Time</Translate>
            </span>
          </dt>
          <dd>
            {telecallerInOutEntity.eventTime ? (
              <TextFormat value={telecallerInOutEntity.eventTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eventDate">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.eventDate">Event Date</Translate>
            </span>
          </dt>
          <dd>{telecallerInOutEntity.eventDate}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{telecallerInOutEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {telecallerInOutEntity.createdAt ? (
              <TextFormat value={telecallerInOutEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{telecallerInOutEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {telecallerInOutEntity.updatedAt ? (
              <TextFormat value={telecallerInOutEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="automatedPerformanceTestingApp.telecallerInOut.telecaller">Telecaller</Translate>
          </dt>
          <dd>{telecallerInOutEntity.telecaller ? telecallerInOutEntity.telecaller.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/telecaller-in-out" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/telecaller-in-out/${telecallerInOutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TelecallerInOutDetail;
