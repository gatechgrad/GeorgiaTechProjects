VERSION 5.00
Begin VB.Form frmModWarrant 
   Caption         =   "Modify Warrant"
   ClientHeight    =   9255
   ClientLeft      =   165
   ClientTop       =   -3105
   ClientWidth     =   5325
   LinkTopic       =   "Form9"
   ScaleHeight     =   9255
   ScaleWidth      =   5325
   StartUpPosition =   3  'Windows Default
   Begin VB.TextBox txtBetween10 
      Height          =   285
      Left            =   4560
      TabIndex        =   43
      Top             =   7680
      Width           =   615
   End
   Begin VB.TextBox txtBetween9 
      Height          =   285
      Left            =   4080
      TabIndex        =   42
      Top             =   7680
      Width           =   375
   End
   Begin VB.TextBox txtBetween8 
      Height          =   285
      Left            =   3480
      TabIndex        =   41
      Text            =   "SSN"
      Top             =   7680
      Width           =   495
   End
   Begin VB.TextBox txtBetween7 
      Height          =   285
      Left            =   2400
      TabIndex        =   40
      Text            =   "LName"
      Top             =   7680
      Width           =   855
   End
   Begin VB.TextBox txtBetween6 
      Height          =   285
      Left            =   1440
      TabIndex        =   39
      Text            =   "FName"
      Top             =   7680
      Width           =   855
   End
   Begin VB.TextBox txtBetween5 
      Height          =   285
      Left            =   4560
      TabIndex        =   38
      Top             =   7320
      Width           =   615
   End
   Begin VB.TextBox txtBetween4 
      Height          =   285
      Left            =   4080
      TabIndex        =   37
      Top             =   7320
      Width           =   375
   End
   Begin VB.TextBox txtBetween3 
      Height          =   285
      Left            =   3480
      TabIndex        =   36
      Text            =   "SSN"
      Top             =   7320
      Width           =   495
   End
   Begin VB.TextBox txtBetween2 
      Height          =   285
      Left            =   2400
      TabIndex        =   35
      Text            =   "LName"
      Top             =   7320
      Width           =   855
   End
   Begin VB.TextBox txtBetween1 
      Height          =   285
      Left            =   1440
      TabIndex        =   34
      Text            =   "FName"
      Top             =   7320
      Width           =   855
   End
   Begin VB.TextBox txtTo5 
      Height          =   285
      Left            =   4560
      TabIndex        =   33
      Top             =   5520
      Width           =   615
   End
   Begin VB.TextBox txtTo4 
      Height          =   285
      Left            =   4080
      TabIndex        =   32
      Top             =   5520
      Width           =   375
   End
   Begin VB.TextBox txtTo3 
      Height          =   285
      Left            =   3480
      TabIndex        =   31
      Text            =   "SSN"
      Top             =   5520
      Width           =   495
   End
   Begin VB.TextBox txtTo2 
      Height          =   285
      Left            =   2400
      TabIndex        =   30
      Text            =   "LName"
      Top             =   5520
      Width           =   855
   End
   Begin VB.TextBox txtTo1 
      Height          =   285
      Left            =   1440
      TabIndex        =   29
      Text            =   "FName"
      Top             =   5520
      Width           =   855
   End
   Begin VB.TextBox txtFrom5 
      Height          =   285
      Left            =   4560
      TabIndex        =   28
      Top             =   3720
      Width           =   615
   End
   Begin VB.TextBox txtFrom4 
      Height          =   285
      Left            =   4080
      TabIndex        =   27
      Top             =   3720
      Width           =   375
   End
   Begin VB.TextBox txtFrom3 
      Height          =   285
      Left            =   3480
      TabIndex        =   26
      Text            =   "SSN"
      Top             =   3720
      Width           =   495
   End
   Begin VB.TextBox txtFrom2 
      Height          =   285
      Left            =   2400
      TabIndex        =   25
      Text            =   "LName"
      Top             =   3720
      Width           =   855
   End
   Begin VB.CommandButton cmdSave 
      Caption         =   "Save Changes"
      Height          =   495
      Left            =   3720
      TabIndex        =   24
      Top             =   8640
      Width           =   1335
   End
   Begin VB.CommandButton cmdCancel 
      Caption         =   "Cancel"
      Height          =   495
      Left            =   2160
      TabIndex        =   23
      Top             =   8640
      Width           =   1335
   End
   Begin VB.CommandButton cmdRemoveBetween 
      Caption         =   "Remove"
      Height          =   255
      Left            =   240
      TabIndex        =   22
      Top             =   8040
      Width           =   975
   End
   Begin VB.CommandButton cmdAddBetween 
      Caption         =   "Add"
      Height          =   255
      Left            =   240
      TabIndex        =   21
      Top             =   7320
      Width           =   975
   End
   Begin VB.CommandButton cmdRemoveTo 
      Caption         =   "Remove"
      Height          =   255
      Left            =   240
      TabIndex        =   20
      Top             =   5880
      Width           =   975
   End
   Begin VB.CommandButton cmdAddTo 
      Caption         =   "Add"
      Height          =   255
      Left            =   240
      TabIndex        =   19
      Top             =   5520
      Width           =   975
   End
   Begin VB.TextBox txtFrom1 
      Height          =   285
      Left            =   1440
      TabIndex        =   18
      Text            =   "FName"
      Top             =   3720
      Width           =   855
   End
   Begin VB.CommandButton cmdRemoveFrom 
      Caption         =   "Remove"
      Height          =   255
      Left            =   240
      TabIndex        =   17
      Top             =   4080
      Width           =   975
   End
   Begin VB.CommandButton cmdAddFrom 
      Caption         =   "Add"
      Height          =   255
      Left            =   240
      TabIndex        =   16
      Top             =   3720
      Width           =   975
   End
   Begin VB.TextBox txtTo 
      Height          =   285
      Left            =   2760
      TabIndex        =   15
      Top             =   2400
      Width           =   1335
   End
   Begin VB.TextBox txtFrom 
      Height          =   285
      Left            =   960
      TabIndex        =   13
      Top             =   2400
      Width           =   1335
   End
   Begin VB.TextBox txtIssued 
      Height          =   285
      Left            =   720
      TabIndex        =   12
      Top             =   2040
      Width           =   1215
   End
   Begin VB.TextBox txtDistrict 
      Height          =   285
      Left            =   120
      TabIndex        =   10
      Top             =   1680
      Width           =   1455
   End
   Begin VB.TextBox txtCourt 
      Height          =   285
      Left            =   120
      TabIndex        =   9
      Top             =   1320
      Width           =   1455
   End
   Begin VB.ComboBox cmbID 
      Height          =   315
      Left            =   3600
      TabIndex        =   8
      Top             =   960
      Width           =   1095
   End
   Begin VB.TextBox txtJudgeName 
      Height          =   285
      Left            =   1320
      TabIndex        =   6
      Top             =   960
      Width           =   1815
   End
   Begin VB.TextBox txtWarNum 
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   18
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   1680
      TabIndex        =   5
      Top             =   120
      Width           =   2055
   End
   Begin VB.Frame frameFrom 
      Caption         =   "Valid for correspondence from the following persons"
      Height          =   1575
      Left            =   0
      TabIndex        =   44
      Top             =   2880
      Width           =   5295
      Begin VB.ListBox listFrom 
         Height          =   450
         Left            =   240
         TabIndex        =   47
         Top             =   240
         Width           =   4815
      End
   End
   Begin VB.Frame frameTo 
      Caption         =   "Valid for correspondence to the following persons"
      Height          =   1575
      Left            =   0
      TabIndex        =   45
      Top             =   4680
      Width           =   5295
      Begin VB.ListBox listTo 
         Height          =   450
         Left            =   240
         TabIndex        =   48
         Top             =   240
         Width           =   4815
      End
   End
   Begin VB.Frame frameTween 
      Caption         =   "Valid for correspondence between the following persons"
      Height          =   1935
      Left            =   0
      TabIndex        =   46
      Top             =   6480
      Width           =   5295
      Begin VB.ListBox listBetween 
         Height          =   450
         Left            =   240
         TabIndex        =   49
         Top             =   240
         Width           =   4815
      End
      Begin VB.Label lbl9 
         Caption         =   "to"
         Height          =   375
         Left            =   1200
         TabIndex        =   50
         Top             =   1200
         Width           =   255
      End
   End
   Begin VB.Label lbl8 
      Caption         =   "to"
      Height          =   255
      Left            =   2400
      TabIndex        =   14
      Top             =   2400
      Width           =   615
   End
   Begin VB.Label lbl6 
      Caption         =   "Issued"
      Height          =   255
      Left            =   120
      TabIndex        =   11
      Top             =   2040
      Width           =   615
   End
   Begin VB.Label lbl3 
      Caption         =   "ID"
      Height          =   255
      Left            =   3360
      TabIndex        =   7
      Top             =   960
      Width           =   255
   End
   Begin VB.Label lbl1 
      Caption         =   "Warrant "
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   18
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   495
      Left            =   120
      TabIndex        =   4
      Top             =   120
      Width           =   1455
   End
   Begin VB.Label lbl2 
      Caption         =   "Issued by judge"
      Height          =   255
      Left            =   120
      TabIndex        =   3
      Top             =   960
      Width           =   1215
   End
   Begin VB.Label lbl4 
      Caption         =   "Judge"
      Height          =   255
      Left            =   1680
      TabIndex        =   2
      Top             =   1320
      Width           =   495
   End
   Begin VB.Label lbl5 
      Caption         =   "Court"
      Height          =   255
      Left            =   1680
      TabIndex        =   1
      Top             =   1680
      Width           =   615
   End
   Begin VB.Label lbl7 
      Caption         =   "Valid From"
      Height          =   375
      Left            =   120
      TabIndex        =   0
      Top             =   2400
      Width           =   855
   End
End
Attribute VB_Name = "frmModWarrant"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Public iWarrantNumber As Integer
Dim strFromMap(256) As String
Dim strToMap(256) As String
Dim strBetweenMap1(256) As String
Dim strBetweenMap2(256) As String

Public Sub setWarrant(i As Integer)
    Dim strQuery As String
    Dim rs As Recordset
    Dim iJudge As Integer
    
    Set dbPredator = OpenDatabase(dbFile)
    
    iWarrantNumber = i
    
    
    '*** Reset All Fields ***
    txtWarNum.text = ""
    cmbID.text = ""
    cmbID.Clear
    txtIssued.text = ""
    txtFrom.text = ""
    txtTo.text = ""
    txtJudgeName.text = ""
    txtCourt.text = ""
    txtDistrict.text = ""
    listFrom.Clear
    listTo.Clear
    listBetween.Clear
    
    '*** Insert the warrant number into the box ***
    If (i > -1) Then
        txtWarNum.text = i
    End If
    
'    strQuery = "SELECT warrants.id, judge, issue_date, beg_date, end_date, FROM warrants, judge" & _
'                "WHERE warrants.id = " & iWarrantNumber & " " & _
'                "AND judge.id = warrants.judge;"

    strQuery = "SELECT id, judge, issue_date, beg_date, end_date FROM warrants " & _
                "WHERE id = " & iWarrantNumber & "; "
                
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    If Not rs.EOF Then
        txtWarNum.text = Trim(rs.Fields(0))
        cmbID.text = Trim(rs.Fields(1))
        txtIssued.text = Trim(rs.Fields(2))
        txtFrom.text = Trim(rs.Fields(3))
        txtTo.text = Trim(rs.Fields(4))
        
        displayFromPeople
        displayToPeople
        displayBetweenPeople
        
        displayJudgeInfo (Trim(rs.Fields(1)))
    Else
        iWarrantNumber = -1
    
    End If
    
    
    '*** Add to the list of judge ID's ***
    strQuery = "SELECT DISTINCT id FROM judge;"
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        cmbID.AddItem (Trim(rs.Fields(0)))
        rs.MoveNext
    Wend
    
        
        
    
    dbPredator.Close
End Sub

Private Sub cmbID_Click()
    displayJudgeInfo (cmbID.text)

End Sub

Private Sub cmdAddFrom_Click()
    Dim updateQuery As QueryDef
    Dim rs As Recordset
    
    Dim strQuery As String
    Dim iIndex As Integer
    
    Set dbPredator = OpenDatabase(dbFile)
    
    iIndex = getAutoNumber("person", "id")
    
    addPerson iIndex, txtFrom1.text, txtFrom2.text, txtFrom3.text & txtFrom4.text & txtFrom5.text
    
    strQuery = "INSERT INTO valid_from(warrant, person) " & _
    "VALUES (" & iWarrantNumber & ", " & iIndex & ")"
    
      Set updateQuery = dbPredator.CreateQueryDef("", strQuery)
      updateQuery.Execute
    
    
    txtFrom1.text = ""
    txtFrom2.text = ""
    txtFrom3.text = ""
    txtFrom4.text = ""
    txtFrom5.text = ""
    displayFromPeople
    displayBetweenPeople
    displayToPeople


End Sub

Private Sub cmdAddTo_Click()
    Dim updateQuery As QueryDef
    Dim rs As Recordset
    
    Dim strQuery As String
    Dim iIndex As Integer
    
    Set dbPredator = OpenDatabase(dbFile)
    
    iIndex = getAutoNumber("person", "id")
    
    iIndex = addPerson(iIndex, txtTo1.text, txtTo2.text, txtTo3.text & txtTo4.text & txtTo5.text)
    
    strQuery = "INSERT INTO valid_to(warrant, person) " & _
    "VALUES (" & iWarrantNumber & ", " & iIndex & ")"
    
    Set updateQuery = dbPredator.CreateQueryDef("", strQuery)
    updateQuery.Execute
    
    
    txtTo1.text = ""
    txtTo2.text = ""
    txtTo3.text = ""
    txtTo4.text = ""
    txtTo5.text = ""
    
    displayToPeople
    displayFromPeople
    displayBetweenPeople

End Sub

Private Sub cmdAddBetween_Click()
    Dim updateQuery As QueryDef
    Dim rs As Recordset
    
    Dim strQuery As String
    Dim iIndex1 As Integer
    Dim iIndex2 As Integer
    
    Set dbPredator = OpenDatabase(dbFile)
    
    'add the first person if not in the database
    iIndex1 = getAutoNumber("person", "id")
    addPerson iIndex1, txtBetween1.text, txtBetween2.text, txtBetween3.text & txtBetween4.text & txtBetween5.text
    
    'add second person if not in the database
    iIndex2 = getAutoNumber("person", "id")
    addPerson iIndex2, txtBetween6.text, txtBetween7.text, txtBetween8.text & txtBetween9.text & txtBetween10.text
    
    strQuery = "INSERT INTO valid_between(warrant, person_1, person_2) " & _
    "VALUES (" & iWarrantNumber & ", " & iIndex1 & ", " & iIndex2 & ")"
    
    Set updateQuery = dbPredator.CreateQueryDef("", strQuery)
    updateQuery.Execute
        
    txtBetween1.text = ""
    txtBetween2.text = ""
    txtBetween3.text = ""
    txtBetween4.text = ""
    txtBetween5.text = ""
    txtBetween6.text = ""
    txtBetween7.text = ""
    txtBetween8.text = ""
    txtBetween9.text = ""
    txtBetween10.text = ""
    
    displayBetweenPeople
    displayFromPeople
    displayToPeople
End Sub

Private Sub cmdCancel_Click()
    frmModWarrant.Visible = False
    frmDataMan.Visible = True
End Sub

Private Sub cmdRemoveBetween_Click()
    Dim strQuery As String
    Dim deleteQuery As QueryDef
    
    Set dbPredator = OpenDatabase(dbFile)
    
    
    strQuery = "DELETE FROM valid_between " & _
               "WHERE warrant = " & iWarrantNumber & " " & _
               "AND person_1 = " & strBetweenMap1(listBetween.ListIndex) & " " & _
               "AND person_2 = " & strBetweenMap2(listBetween.ListIndex) & ";"
    Set deleteQuery = dbPredator.CreateQueryDef("", strQuery)
    deleteQuery.Execute
    
    dbPredator.Close
    displayBetweenPeople

End Sub

Private Sub cmdRemoveFrom_Click()
    Dim strQuery As String
    Dim deleteQuery As QueryDef
    
    Set dbPredator = OpenDatabase(dbFile)
    
    
    strQuery = "DELETE FROM valid_from " & _
               "WHERE warrant = " & iWarrantNumber & " " & _
               "AND person = " & strFromMap(listFrom.ListIndex) & ";"
               
    Set deleteQuery = dbPredator.CreateQueryDef("", strQuery)
    deleteQuery.Execute
    
    dbPredator.Close
    displayFromPeople
End Sub

Private Sub cmdRemoveTo_Click()
    Dim strQuery As String
    Dim deleteQuery As QueryDef
    
    Set dbPredator = OpenDatabase(dbFile)
    
    
    strQuery = "DELETE FROM valid_to " & _
               "WHERE warrant = " & iWarrantNumber & " " & _
               "AND person = " & strToMap(listTo.ListIndex) & ";"
               
    Set deleteQuery = dbPredator.CreateQueryDef("", strQuery)
    deleteQuery.Execute
    
    dbPredator.Close
    displayToPeople

End Sub

Private Sub cmdSave_Click()
    Dim strErrors As String

    'Cheap way to detect errors
    strErrors = ""
    
    If (Not IsNumeric(txtWarNum.text)) Then
        strErrors = strErrors & "    - Warrant ID is a non-numeric value" & Chr(13) & Chr(10)
    End If
    
    If (txtJudgeName.text = "") Then
        strErrors = strErrors & "    - A name must be entered for the judge" & Chr(13) & Chr(10)
    End If
    
    If (Not IsNumeric(cmbID.text)) Then
        strErrors = strErrors & "    - Judge ID is a non-numeric value" & Chr(13) & Chr(10)
    End If
    
    If (txtCourt.text = "") Then
        strErrors = strErrors & "    - The type must be entered for the judge" & Chr(13) & Chr(10)
    End If

    If (txtCourt.text = "") Then
        strErrors = strErrors & "    - The court must be entered for the judge" & Chr(13) & Chr(10)
    End If
    
    If (Not IsDate(txtIssued.text)) Then
        strErrors = strErrors & "    - Must enter an acceptable issue date" & Chr(13) & Chr(10)
    End If
    
    If (Not IsDate(txtFrom.text)) Then
        strErrors = strErrors & "    - Must enter an acceptable valid 'from' date" & Chr(13) & Chr(10)
    End If
    
    If (Not IsDate(txtTo.text)) Then
        strErrors = strErrors & "    - Must enter an acceptable valid 'to' date" & Chr(13) & Chr(10)
    End If

    
    
    If (strErrors = "") Then
        If iWarrantNumber < 0 Then
            addWarrant
        Else
            updateWarrant
        End If
        frmModWarrant.Visible = False
        frmDataMan.Visible = True
    Else
        MsgBox ("The warrant had fatal errors: " & Chr(10) & Chr(13) & strErrors)
    End If
End Sub
Private Sub addWarrant()
    Dim strQuery As String
    Dim addQuery As QueryDef
    Dim rs As Recordset
    
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT id FROM judge WHERE id = " & cmbID.text & ";"
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    If rs.EOF Then
        addJudge
    Else
        updateJudge
    End If
    
    strQuery = "INSERT INTO warrants(id, judge, issue_date, beg_date, end_date) " & _
    "VALUES(" & txtWarNum.text & ", " & cmbID.text & ", '" & txtIssued.text & "', '" & txtFrom.text & "', '" & txtTo.text & "');"
    
    Set addQuery = dbPredator.CreateQueryDef("", strQuery)
    addQuery.Execute
        
    dbPredator.Close

End Sub
Private Sub updateWarrant()
    Dim strQuery As String
    Dim updateQuery As QueryDef
    Dim rs As Recordset
    
    Set dbPredator = OpenDatabase(dbFile)
    
    
    strQuery = "SELECT id FROM judge WHERE id = " & cmbID.text & ";"
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    If rs.EOF Then
        addJudge
    Else
        updateJudge
    End If
    
    strQuery = "UPDATE warrants " & _
    "SET judge = " & cmbID.text & ", " & _
    "issue_date = '" & txtIssued.text & "', " & _
    "beg_date = '" & txtFrom.text & "', " & _
    "end_date = '" & txtTo.text & "' " & _
    "WHERE id = " & txtWarNum.text & ""
    
    Set addQuery = dbPredator.CreateQueryDef("", strQuery)
    addQuery.Execute
        
    dbPredator.Close

End Sub
Private Sub addJudge()
    Dim addQuery As QueryDef
    Dim strQuery As String
    
    Set dbPredator = OpenDatabase(dbFile)
    
    
    strQuery = "INSERT INTO judge(id, name, type, court) " & _
    "VALUES(" & cmbID.text & ", '" & txtJudgeName.text & "', '" & txtCourt.text & "', '" & txtDistrict.text & "');"
    
    Set addQuery = dbPredator.CreateQueryDef("", strQuery)
    addQuery.Execute

End Sub
Private Sub updateJudge()
    Dim addQuery As QueryDef
    Dim strQuery As String
    Dim updateJudge As Boolean
    Dim rs As Recordset
    
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT name, type, court FROM judge " & _
                "WHERE id = " & cmbID.text & ";"
                    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    If Not rs.EOF Then
        If Not rs.Fields(0) = txtJudgeName.text _
            Or Not rs.Fields(1) = txtCourt.text _
            Or Not rs.Fields(2) = txtDistrict.text Then
        updateJudge = MsgBox("This will update the information for judge" & cmbID.text & ".  Do you want to do this?", vbYesNo)
    
            If updateJudge Then
                strQuery = "UPDATE judge " & _
                            "SET name = '" & txtJudgeName.text & "', " & _
                            "type = '" & txtCourt.text & "', " & _
                            "court = '" & txtDistrict.text & "' " & _
                            "WHERE id = " & cmbID.text & ";"

                Set addQuery = dbPredator.CreateQueryDef("", strQuery)
                addQuery.Execute
            End If
        Else
            'Judge not changed
            
        End If
    
    End If
End Sub


Private Sub Form_Unload(Cancel As Integer)
    frmModWarrant.Visible = False
    frmDataMan.Visible = True
End Sub

Private Sub displayJudgeInfo(iJudge As Integer)
    Dim strQuery As String
    Dim rs As Recordset
    
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT name, type, court FROM judge " & _
                "WHERE id = " & iJudge & "; "
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    If Not rs.EOF Then
        txtJudgeName.text = Trim(rs.Fields(0))
        txtCourt.text = Trim(rs.Fields(1))
        txtDistrict.text = Trim(rs.Fields(2))
    End If
    
End Sub
Private Sub displayFromPeople()
    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database
    Dim i As Integer
    Dim iValue As Integer
    

    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT fname, lname, SSN, person.id FROM person, valid_from " & _
               "WHERE person.id = valid_from.person " & _
               "AND valid_from.warrant = " & iWarrantNumber & ";"
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    
    
    listFrom.Clear
    i = 0
    While (Not rs.EOF)
        strFromMap(i) = Trim(rs.Fields(3))
        listFrom.AddItem (Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & " (" & Trim(rs.Fields(2)) & ")")
        i = i + 1
        rs.MoveNext
    Wend

    dbPredator.Close
End Sub
Private Sub displayToPeople()
    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database
    Dim i As Integer

    Set dbPredator = OpenDatabase(dbFile)

    strQuery = "SELECT fname, lname, SSN, person.id FROM person, valid_to " & _
               "WHERE person.id = valid_to.person " & _
               "AND valid_to.warrant = " & iWarrantNumber & ";"

    Set rs = dbPredator.OpenRecordset(strQuery)
    
    listTo.Clear
    i = 0
    While (Not rs.EOF)
        strToMap(i) = Trim(rs.Fields(3))
        listTo.AddItem Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & " (" & Trim(rs.Fields(2)) & ")"
        i = i + 1
        rs.MoveNext
    Wend

    dbPredator.Close
End Sub
Private Sub displayBetweenPeople()
    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database
    Dim i As Integer

    Set dbPredator = OpenDatabase(dbFile)

    strQuery = "SELECT a.fname, a.lname, a.SSN, b.fname, b.lname, b.SSN, a.id, b.id FROM person a, person b, valid_between " & _
               "WHERE a.id = valid_between.person_1 " & _
               "AND b.id = valid_between.person_2 " & _
               "AND valid_between.warrant = " & iWarrantNumber & ";"
               
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    listBetween.Clear
    i = 0
    While (Not rs.EOF)
        strBetweenMap1(i) = Trim(rs.Fields(6))
        strBetweenMap2(i) = Trim(rs.Fields(7))
        listBetween.AddItem Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & " (" & Trim(rs.Fields(2)) & ")" & _
                            " to " & Trim(rs.Fields(3)) & " " & Trim(rs.Fields(4)) & " (" & Trim(rs.Fields(5)) & ")"
        i = i + 1
        rs.MoveNext
    Wend

    dbPredator.Close
End Sub



Private Function addPerson(ID As Integer, fname As String, lname As String, SSN As String)
    Dim updateQuery As QueryDef
    Dim rs As Recordset
    Dim strQuery As String
    Dim updatePerson As Boolean
    
    If IsNumeric(SSN) Then
    
        Set dbPredator = OpenDatabase(dbFile)
    
    
        'check to see if the person is already in the database
        strQuery = "SELECT * FROM person " & _
                    "WHERE SSN = " & SSN & ";"
    
        Set rs = dbPredator.OpenRecordset(strQuery)
    
        If (Not rs.EOF) Then
            'Update the person
            updatePerson = MsgBox("This will update the information for the person with SSN " & SSN & ".  Do you want to do this?")
        
        
            If updatePerson Then
                strQuery = "UPDATE person " & _
                            "SET fname = '" & fname & "', " & _
                            "lname = '" & lname & "' " & _
                            "WHERE SSN = " & SSN & ";"
                Set updateQuery = dbPredator.CreateQueryDef("", strQuery)
                updateQuery.Execute
    
            End If
        Else
            'Add the person
            strQuery = "INSERT INTO person(id, fname, lname, SSN) " & _
            "VALUES (" & ID & ", '" & fname & "', '" & lname & "', '" & SSN & "')"
        
            Set updateQuery = dbPredator.CreateQueryDef("", strQuery)
            updateQuery.Execute
    
        End If
   
        '*** return the ID of the person added or updated ***
        strQuery = "SELECT id FROM person " & _
                    "WHERE SSN = " & SSN & ";"
    
        Set rs = dbPredator.OpenRecordset(strQuery)
    
        If (Not rs.EOF) Then
            addPerson = rs.Fields(0)
        End If
    
    
        dbPredator.Close
    Else
        MsgBox "SSN of person attempted to add was not a number"
    End If
End Function

