Attribute VB_Name = "AutoNumber"
Public Const dbFile = "H:\gatech\cs4400\CoCPred\Predator\Predator.mdb"

Public Function getAutoNumber(strTableName As String, strAttribute As String)
    Dim rs As Recordset
    Dim strQuery As String
    Dim iIndex As Integer
    
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT MAX(" & strAttribute & ") FROM " & strTableName
    
    Set rs = dbPredator.OpenRecordset(strQuery)
      
      If IsNull(rs.Fields(0)) Then
        iIndex = 0
      Else
        iIndex = rs.Fields(0) + 1
      End If
  
    
    dbPredator.Close
    
    getAutoNumber = iIndex

End Function
