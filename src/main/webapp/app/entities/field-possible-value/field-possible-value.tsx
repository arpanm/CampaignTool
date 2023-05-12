import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFieldPossibleValue } from 'app/shared/model/field-possible-value.model';
import { getEntities, reset } from './field-possible-value.reducer';

export const FieldPossibleValue = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const fieldPossibleValueList = useAppSelector(state => state.fieldPossibleValue.entities);
  const loading = useAppSelector(state => state.fieldPossibleValue.loading);
  const totalItems = useAppSelector(state => state.fieldPossibleValue.totalItems);
  const links = useAppSelector(state => state.fieldPossibleValue.links);
  const entity = useAppSelector(state => state.fieldPossibleValue.entity);
  const updateSuccess = useAppSelector(state => state.fieldPossibleValue.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="field-possible-value-heading" data-cy="FieldPossibleValueHeading">
        <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.home.title">Field Possible Values</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/field-possible-value/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.home.createLabel">
              Create new Field Possible Value
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={fieldPossibleValueList ? fieldPossibleValueList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {fieldPossibleValueList && fieldPossibleValueList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('value')}>
                    <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.value">Value</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdAt')}>
                    <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.createdAt">Created At</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.updatedAt">Updated At</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.field">Field</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fieldPossibleValueList.map((fieldPossibleValue, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/field-possible-value/${fieldPossibleValue.id}`} color="link" size="sm">
                        {fieldPossibleValue.id}
                      </Button>
                    </td>
                    <td>{fieldPossibleValue.value}</td>
                    <td>{fieldPossibleValue.isActive ? 'true' : 'false'}</td>
                    <td>{fieldPossibleValue.createdBy}</td>
                    <td>
                      {fieldPossibleValue.createdAt ? (
                        <TextFormat type="date" value={fieldPossibleValue.createdAt} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{fieldPossibleValue.updatedBy}</td>
                    <td>
                      {fieldPossibleValue.updatedAt ? (
                        <TextFormat type="date" value={fieldPossibleValue.updatedAt} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {fieldPossibleValue.field ? (
                        <Link to={`/field/${fieldPossibleValue.field.id}`}>{fieldPossibleValue.field.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/field-possible-value/${fieldPossibleValue.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/field-possible-value/${fieldPossibleValue.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/field-possible-value/${fieldPossibleValue.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.home.notFound">
                  No Field Possible Values found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FieldPossibleValue;
