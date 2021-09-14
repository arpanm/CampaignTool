import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './telecaller.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TelecallerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const telecallerEntity = useAppSelector(state => state.telecaller.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="telecallerDetailsHeading">
          <Translate contentKey="campaignToolApp.telecaller.detail.title">Telecaller</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{telecallerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="campaignToolApp.telecaller.name">Name</Translate>
            </span>
          </dt>
          <dd>{telecallerEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="campaignToolApp.telecaller.email">Email</Translate>
            </span>
          </dt>
          <dd>{telecallerEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="campaignToolApp.telecaller.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{telecallerEntity.phone}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="campaignToolApp.telecaller.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {telecallerEntity.startDate ? (
              <TextFormat value={telecallerEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="campaignToolApp.telecaller.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {telecallerEntity.endDate ? <TextFormat value={telecallerEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="campaignToolApp.telecaller.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{telecallerEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.telecaller.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{telecallerEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.telecaller.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {telecallerEntity.createdAt ? (
              <TextFormat value={telecallerEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.telecaller.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{telecallerEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.telecaller.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {telecallerEntity.updatedAt ? (
              <TextFormat value={telecallerEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/telecaller" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/telecaller/${telecallerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TelecallerDetail;
