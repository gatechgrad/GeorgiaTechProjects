VERSION 5.00
Begin VB.Form frmShady 
   Caption         =   "Find Shady Characters"
   ClientHeight    =   3450
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4500
   LinkTopic       =   "Form15"
   ScaleHeight     =   3450
   ScaleWidth      =   4500
   StartUpPosition =   3  'Windows Default
   Begin VB.ListBox listShady 
      Height          =   2205
      Left            =   240
      TabIndex        =   5
      Top             =   1080
      Width           =   2175
   End
   Begin VB.CommandButton cmdBack 
      Caption         =   "Back"
      Height          =   375
      Left            =   3000
      TabIndex        =   4
      Top             =   1680
      Width           =   1335
   End
   Begin VB.CommandButton cmdFindBad 
      Caption         =   "Find Bad Dudes"
      Height          =   375
      Left            =   3000
      TabIndex        =   3
      Top             =   1200
      Width           =   1335
   End
   Begin VB.TextBox txtDeg 
      Height          =   285
      Left            =   2760
      TabIndex        =   1
      Text            =   "3"
      Top             =   120
      Width           =   375
   End
   Begin VB.Label lbl2 
      Caption         =   "separation from a message intercepted by a warrant."
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   735
      Left            =   120
      TabIndex        =   2
      Top             =   480
      Width           =   4095
   End
   Begin VB.Label lbl1 
      Caption         =   "List everyone with fewer than             degrees of"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   400
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   4095
   End
End
Attribute VB_Name = "frmShady"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdBack_Click()
    frmShady.Visible = False
    frmDataAnal.Visible = True
End Sub

Private Sub cmdFindBad_Click()
    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database
    
    Set dbPredator = OpenDatabase(dbFile)
    
    '*** Find first degree
    strQuery = "SELECT DISTINCT fname, lname FROM person, valid_from, valid_to, valid_between " & _
                "WHERE person.id = valid_from.person " & _
                "OR valid_to.person = person.id " & _
                "OR valid_between.person_1 = person.id " & _
                "OR valid_between.person_2 = person.id"
                
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        listShady.AddItem Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & "(1)"
        rs.MoveNext
    Wend
    
    '*** Find rest
    strQuery = "SELECT DISTINCT fname, lname FROM person, valid_from, valid_to, valid_between " & _
                "WHERE NOT (person.id = valid_from.person " & _
                "OR valid_to.person = person.id " & _
                "OR valid_between.person_1 = person.id " & _
                "OR valid_between.person_2 = person.id)"
                
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        listShady.AddItem Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & "(2)"
        rs.MoveNext
    Wend
    
    
    
    
    
    
    dbPredator.Close
End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmShady.Visible = False
    frmDataAnal.Visible = True

End Sub
