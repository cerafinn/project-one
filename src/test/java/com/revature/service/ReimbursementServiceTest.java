package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.ResolveReimbursementDTO;
import com.revature.exception.ImageNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
public class ReimbursementServiceTest {
  @Mock
  private ReimbursementDao mockedDao;
  private ResolveReimbursementDTO mockedDTO;

  @InjectMocks
  private ReimbursementService systemUnderTest;

//  @Test
//  public void testGetAllReimbursements() throws SQLException {
//  }
//
//  @Test
//  public void testGetAllByUser_EmployeePositive() throws SQLException {
//  }
//
//  @Test
//  public void testGetAllByUser_EmployeeNegative() throws SQLException {
//  }
//
//  @Test
//  public void testaddNewReimbursement_Success() throws SQLException {}
//
//  @Test
//  public void testaddNewReimbursement_Failed() throws SQLException {}
//
//  @Test
//  public void testResolveReimbursement_Success() throws SQLException {}
//
//  @Test
//  public void testResolveReimbursement_Failed() throws SQLException {}
//
//  @Test
//  public void testGetReceipt_Success() throws SQLException, ImageNotFoundException {}
//
//  @Test
//  public void testGetReceipt_Failed() throws SQLException, ImageNotFoundException {}
//
//  @Test
//  public void testGetByStatus_Success() throws SQLException {}
//
//  @Test
//  public void testGetStatus_Failed() throws SQLException {}
//
//  @Test
//  public void getReimbByUserAndStatus_Success() throws SQLException {}
//
//  @Test
//  public void getReimbByUserAndStatus_Failed() throws SQLException {}
}
