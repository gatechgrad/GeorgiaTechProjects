VERSION 5.00
Begin VB.Form frmRFAct 
   Caption         =   "Red Flag Activity"
   ClientHeight    =   3420
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   7410
   LinkTopic       =   "Form12"
   ScaleHeight     =   3420
   ScaleWidth      =   7410
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdOK 
      Caption         =   "OK"
      Height          =   495
      Left            =   2160
      TabIndex        =   3
      Top             =   2400
      Width           =   1215
   End
   Begin VB.CommandButton cmdDisplay 
      Caption         =   "Display Message"
      Height          =   495
      Left            =   240
      TabIndex        =   2
      Top             =   2400
      Width           =   1575
   End
   Begin VB.ListBox listMess 
      Height          =   1425
      Left            =   240
      TabIndex        =   1
      Top             =   720
      Width           =   6855
   End
   Begin VB.Label lblAnnounce 
      Caption         =   "Words ""Robbery"", ""Kidnap"" intercepted the following messages:"
      Height          =   495
      Left            =   240
      TabIndex        =   0
      Top             =   120
      Width           =   4215
   End
End
Attribute VB_Name = "frmRFAct"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Public strWordList As String
Dim iMessageMap(256) As Integer
Dim iMessageCount As Integer


Public Sub addMessage(iMessageNumber As Integer)
    Dim strQuery As String
    Dim rs As Recordset
    Dim rs2 As Recordset
    Dim dbPredator As Database
    
    Set dbPredator = OpenDatabase(dbFile)
    
        '*** Set sender information ***
        strQuery = "SELECT person.fname, person.lname, message.sdate FROM message, addresses, person " & _
                    "WHERE message.id = " & iMessageNumber & _
                    "AND message.from_address = addresses.address " & _
                    "AND addresses.person = person.id"

        Set rs = dbPredator.OpenRecordset(strQuery)
        
        If Not rs.EOF Then
            strTemp = (Trim(rs.Fields(0))) & " " & (Trim(rs.Fields(1))) & " to "
        Else
        
            strQuery = "SELECT message.from_address, message.sdate FROM message " & _
                        "WHERE message.id = " & iMessageNumber

            Set rs = dbPredator.OpenRecordset(strQuery)
            strTemp = (Trim(rs.Fields(0))) & " to "
        End If

        rs.Close
    
            
        '*** Set recipient information ***
            strQuery = "SELECT person.fname, person.lname, person.ssn FROM person, msg_to, addresses " & _
                "WHERE msg_to.msg = " & iMessageNumber & " " & _
                "AND msg_to.address = addresses.address " & _
                "AND addresses.person = person.id;"
                
 
            Set rs = dbPredator.OpenRecordset(strQuery)

            If rs.EOF Then
                strQuery = "SELECT msg_to.address FROM msg_to " & _
                    "WHERE msg_to.msg = " & iMessageNumber
                    
                    
                
 
                Set rs2 = dbPredator.OpenRecordset(strQuery)
                
                While (Not rs2.EOF)
                    strTemp = strTemp & " (" & (Trim(rs2.Fields(0))) & "), "
                    rs2.MoveNext
                Wend
            
            Else
                While (Not rs.EOF)
                    strTemp = strTemp & " " & (Trim(rs.Fields(0))) & " " & (Trim(rs.Fields(1))) & ", "
                    rs.MoveNext
                Wend
            End If
            
            
        strQuery = "SELECT message.sdate FROM message " & _
                    "WHERE message.id = " & iMessageNumber
                    
        Set rs = dbPredator.OpenRecordset(strQuery)
        
        If Not rs.EOF Then
            strTemp = strTemp & (Trim(rs.Fields(0)))
       End If
            
            
            listMess.AddItem strTemp
        
    
    iMessageMap(iMessageCount) = iMessageNumber
    iMessageCount = iMessageCount + 1
    lblAnnounce.Caption = "Words " & strWordList & " intercepted the following messages:"
End Sub
Public Sub addMessage2(iMessageNumber As Integer)
    Dim strQuery As String
    Dim rs As Recordset
    Dim dbPredator As Database
    
    Set dbPredator = OpenDatabase(dbFile)
    
    listMess.AddItem "" & iMessageNumber
    
    iMessageMap(iMessageCount) = iMessageNumber
    iMessageCount = iMessageCount + 1
    lblAnnounce.Caption = "Words " & strWordList & " intercepted the following messages:"
End Sub



Public Sub addMessageOld(iMessageNumber As Integer)
    Dim strQuery As String
    Dim rs As Recordset
    Dim rs2 As Recordset
    Dim dbPredator As Database
    Dim i As Integer
    Dim strTemp As String
    
    Set dbPredator = OpenDatabase(dbFile)


        '*** Set sender information ***
        strQuery = "SELECT person.fname, person.lname, sdate, message.id FROM message, person, addresses " & _
                    "WHERE message.id = " & iMessageNumber & _
                    "AND message.from_address = addresses.address " & _
                    "AND addresses.person = person.id"
        Set rs = dbPredator.OpenRecordset(strQuery)

        i = 0
        While (Not rs.EOF)
    
            strTemp = (Trim(rs.Fields(0))) & " " & (Trim(rs.Fields(1))) & " to "
            
            '*** Set recipient information ***
            strQuery = "SELECT person.fname, person.lname, person.ssn FROM person, msg_to, addresses " & _
                "WHERE msg_to.msg = " & (Trim(rs.Fields(3))) & " " & _
                "AND msg_to.address = addresses.address " & _
                "AND addresses.person = person.id;"
                
                            
            Set rs2 = dbPredator.OpenRecordset(strQuery)

            While (Not rs2.EOF)
                strTemp = strTemp & " " & (Trim(rs2.Fields(0))) & " " & (Trim(rs2.Fields(1))) & ", "
                rs2.MoveNext
            Wend
            
            strTemp = strTemp & " " & Trim(rs.Fields(2))
            listMess.AddItem strTemp
                        
            
            iMessageMap(i) = (Trim(rs.Fields(3)))
            i = i + 1
            
            rs.MoveNext
        Wend
        
        lblAnnounce.Caption = "Words " & strWordList & " intercepted the following messages:"
        

    dbPredator.Close

End Sub

Public Sub clearMessages()
    Dim i As Integer
    listMess.Clear
    
    While i < 256
        iMessageMap(i) = -1
        i = i + 1
    Wend
    
    iMessageCount = 0
    
End Sub

Private Sub cmdDisplay_Click()
    frmShowMess.SetMessage (iMessageMap(listMess.ListIndex))
    
    If frmShowMess.iMessageNumber > -1 Then
        frmShowMess.strGoBack = "RF"

        frmShowMess.Visible = True
        frmWarAct.Visible = False
    Else
        MsgBox "The message that you selected was not available"
    End If
    
    
    
    frmRFAct.Visible = False
    frmShowMess.Visible = True
End Sub

Private Sub cmdOK_Click()
    frmRFAct.Visible = False
    frmDataAnal.Visible = True
End Sub

Private Sub Form_Load()
'List1.AddItem "John Nogood to Sally Badboy, 8/22/2001"
'List1.AddItem "Tim Badseed to John Nogood, 8/25/2001"
End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmRFAct.Visible = False
    frmDataAnal.Visible = True

End Sub
