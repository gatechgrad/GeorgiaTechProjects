VERSION 5.00
Begin VB.Form frmViewWar 
   Caption         =   "View Warrant"
   ClientHeight    =   5550
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   6135
   LinkTopic       =   "Form6"
   ScaleHeight     =   5550
   ScaleWidth      =   6135
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdOK 
      Caption         =   "OK"
      Height          =   495
      Left            =   4680
      TabIndex        =   11
      Top             =   4920
      Width           =   1215
   End
   Begin VB.ListBox listBetween 
      Height          =   450
      Left            =   120
      TabIndex        =   10
      Top             =   4200
      Width           =   5895
   End
   Begin VB.ListBox listTo 
      Height          =   450
      Left            =   120
      TabIndex        =   8
      Top             =   3240
      Width           =   5895
   End
   Begin VB.ListBox listFrom 
      Height          =   450
      Left            =   120
      TabIndex        =   6
      Top             =   2280
      Width           =   5895
   End
   Begin VB.Label lblWhen 
      Caption         =   "Issued 8/20/2001"
      Height          =   255
      Left            =   120
      TabIndex        =   12
      Top             =   1320
      Width           =   1455
   End
   Begin VB.Label lbl3 
      Caption         =   "Valid for electronic correspondence between the following persons:"
      Height          =   375
      Left            =   120
      TabIndex        =   9
      Top             =   3840
      Width           =   4815
   End
   Begin VB.Label lbl2 
      Caption         =   "Valid for electronic correspondence to the following persons:"
      Height          =   375
      Left            =   120
      TabIndex        =   7
      Top             =   2880
      Width           =   4455
   End
   Begin VB.Label lbl1 
      Caption         =   "Valid for electronic correspondence from the following persons:"
      Height          =   375
      Left            =   120
      TabIndex        =   5
      Top             =   1920
      Width           =   4455
   End
   Begin VB.Label lblValid 
      Caption         =   "Valid From 8/20/2001 to 9/20/2001"
      Height          =   375
      Left            =   120
      TabIndex        =   4
      Top             =   1560
      Width           =   3255
   End
   Begin VB.Label lblCourt 
      Caption         =   "9th District Court"
      Height          =   255
      Left            =   120
      TabIndex        =   3
      Top             =   1080
      Width           =   1575
   End
   Begin VB.Label lblJudgePos 
      Caption         =   "District Court Judge"
      Height          =   255
      Left            =   120
      TabIndex        =   2
      Top             =   840
      Width           =   1575
   End
   Begin VB.Label lblIssuedBy 
      Caption         =   "Issued by judge Santha P. Smith, ID 12345"
      Height          =   375
      Left            =   120
      TabIndex        =   1
      Top             =   600
      Width           =   3855
   End
   Begin VB.Label lblWarNum 
      Caption         =   "Warrant 1234567"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   18
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   615
      Left            =   120
      TabIndex        =   0
      Top             =   0
      Width           =   3375
   End
End
Attribute VB_Name = "frmViewWar"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Public iWarrantNumber As Integer
Public Sub setWarrant(i As Integer)
    Dim dbPredator As Database
    
    Set dbPredator = OpenDatabase("Predator.mdb")
    
    iWarrantNumber = i
    
    '*** Reset All Fields ***
    lblWarNum.Caption = ""
    lblIssuedBy.Caption = ""
    lblJudgePos.Caption = ""
    lblCourt.Caption = ""
    lblWhen.Caption = ""
    lblValid.Caption = ""
    
    '*** Insert the warrant number into the box ***
    strQuery = "SELECT warrants.id, judge, issue_date, beg_date, end_date, name, type, court FROM warrants, judge " & _
                "WHERE warrants.id = " & iWarrantNumber & " " & _
                "AND judge.id = warrants.judge;"
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    If Not rs.EOF Then
        lblWarNum.Caption = Trim(rs.Fields(0))
        lblIssuedBy.Caption = "Issued by judge " & Trim(rs.Fields(5)) & ", ID " & Trim(rs.Fields(1))
        lblJudgePos.Caption = Trim(rs.Fields(6)) & " Judge"
        lblCourt.Caption = Trim(rs.Fields(7)) & " Court"
        lblWhen.Caption = "Issued " & Trim(rs.Fields(2))
        lblValid.Caption = "Valid from " & Trim(rs.Fields(3)) & " to " & Trim(rs.Fields(4))
        
        displayFromPeople
        displayToPeople
        displayBetweenPeople
    Else
        iWarrantNumber = -1
    End If

End Sub
Private Sub cmdOK_Click()
    frmViewWar.Visible = False
End Sub
Private Sub Form_Load()
'List1.AddItem ("Clarence Badboy (SSN 111-11-1111)")
'List1.AddItem ("Clarice Nogood (SSN 222-22-2222)")
'List2.AddItem ("Clarence Badboy (SSN 111-11-1111)")
'List3.AddItem ("from Chuck Nastydude (SSN 333-33-3333) to Clarice Nogood (SSN 222-22-2222)")
End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmViewWar.Visible = False
End Sub

Private Sub displayFromPeople()
    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database

    Set dbPredator = OpenDatabase(dbFile)

    strQuery = "SELECT fname, lname, SSN, person.id FROM person, valid_from " & _
               "WHERE person.id = valid_from.person " & _
               "AND valid_from.warrant = " & iWarrantNumber & ";"
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    
    
    listFrom.Clear
    While (Not rs.EOF)
        listFrom.AddItem (Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & " (" & Trim(rs.Fields(2)) & ")")
        rs.MoveNext
    Wend

    dbPredator.Close
End Sub
Private Sub displayToPeople()
    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database

    Set dbPredator = OpenDatabase(dbFile)

    strQuery = "SELECT fname, lname, SSN, person.id FROM person, valid_to " & _
               "WHERE person.id = valid_to.person " & _
               "AND valid_to.warrant = " & iWarrantNumber & ";"

    Set rs = dbPredator.OpenRecordset(strQuery)
    
    listTo.Clear
    While (Not rs.EOF)
        listTo.AddItem Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & " (" & Trim(rs.Fields(2)) & ")"
        rs.MoveNext
    Wend

    dbPredator.Close
End Sub
Private Sub displayBetweenPeople()
    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database

    Set dbPredator = OpenDatabase(dbFile)

    strQuery = "SELECT a.fname, a.lname, a.SSN, b.fname, b.lname, b.SSN, a.id, b.id FROM person a, person b, valid_between " & _
               "WHERE a.id = valid_between.person_1 " & _
               "AND b.id = valid_between.person_2 " & _
               "AND valid_between.warrant = " & iWarrantNumber & ";"
               
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    listBetween.Clear
    While (Not rs.EOF)
        listBetween.AddItem Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & " (" & Trim(rs.Fields(2)) & ")" & _
                            " to " & Trim(rs.Fields(3)) & " " & Trim(rs.Fields(4)) & " (" & Trim(rs.Fields(5)) & ")"
        rs.MoveNext
    Wend

    dbPredator.Close
End Sub


