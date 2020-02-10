VERSION 5.00
Begin VB.Form frmRedSearch 
   Caption         =   "Choose the Red-Flag Words to Search On"
   ClientHeight    =   3180
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4785
   LinkTopic       =   "Form14"
   ScaleHeight     =   3180
   ScaleWidth      =   4785
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdOK 
      Caption         =   "OK"
      Height          =   375
      Left            =   1680
      TabIndex        =   5
      Top             =   1680
      Width           =   735
   End
   Begin VB.ListBox listSelected 
      Height          =   2400
      Left            =   2760
      TabIndex        =   3
      Top             =   480
      Width           =   1215
   End
   Begin VB.CommandButton cmdAdd 
      Caption         =   ">>>>>"
      Height          =   375
      Left            =   1680
      TabIndex        =   1
      Top             =   1080
      Width           =   735
   End
   Begin VB.ListBox listAllWords 
      Height          =   2400
      Left            =   240
      TabIndex        =   0
      Top             =   480
      Width           =   1215
   End
   Begin VB.Label lbl2 
      Caption         =   "Red-Flag words selected:"
      Height          =   255
      Left            =   2400
      TabIndex        =   4
      Top             =   120
      Width           =   1815
   End
   Begin VB.Label lbl1 
      Caption         =   "Red-Flag words:"
      Height          =   255
      Left            =   240
      TabIndex        =   2
      Top             =   120
      Width           =   1215
   End
End
Attribute VB_Name = "frmRedSearch"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdAdd_Click()
    Dim strSearchWord As String
    
    strSearchWord = listAllWords.List(listAllWords.ListIndex)
    
    listSelected.AddItem strSearchWord
    
End Sub

Private Sub cmdOK_Click()
    
    frmRedSearch.Visible = False
    frmRFAct.Visible = True
    
    frmRFAct.clearMessages
    rfSearch
    
    
    
End Sub
Public Sub reset()
    listSelected.Clear

End Sub


Private Sub Form_Load()
    Dim dbPredator As Database
    Dim strQuery As String
    Dim rs As Recordset
    
    Set dbPredator = OpenDatabase(dbFile)
    listAllWords.Clear
    

    strQuery = "SELECT word FROM rfwords"
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        listAllWords.AddItem Trim(rs.Fields(0))
        rs.MoveNext
    Wend

    
    dbPredator.Close
End Sub


Private Sub rfSearch()

    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database
    Dim i As Integer
    
    Set dbPredator = OpenDatabase(dbFile)
    
    
    strQuery = "SELECT msg FROM msg_rfwords, rfwords " & _
                "WHERE rfwords.id = msg_rfwords.rfword " & _
                "AND (0 = 1 "

    i = 0
    frmRFAct.strWordList = ""
    While i < listSelected.ListCount
        strQuery = strQuery & "OR rfwords.word = '" & listSelected.List(i) & "' "
        frmRFAct.strWordList = frmRFAct.strWordList & "'" & listSelected.List(i) & "', "
        i = i + 1
    Wend
    
    strQuery = strQuery & ")"
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        frmRFAct.addMessage Trim(rs.Fields(0))
        rs.MoveNext
    Wend

    
    dbPredator.Close
    
End Sub
