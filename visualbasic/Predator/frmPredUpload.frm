VERSION 5.00
Begin VB.Form frmPredUpload 
   Caption         =   "Predator Upload"
   ClientHeight    =   4560
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   5745
   LinkTopic       =   "Form2"
   Picture         =   "frmPredUpload.frx":0000
   ScaleHeight     =   4560
   ScaleWidth      =   5745
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdCancel 
      Caption         =   "Cancel"
      Height          =   495
      Left            =   4560
      TabIndex        =   7
      Top             =   3480
      Width           =   975
   End
   Begin VB.CommandButton cmdOK 
      Caption         =   "OK"
      Height          =   495
      Left            =   4560
      TabIndex        =   6
      Top             =   2760
      Width           =   975
   End
   Begin VB.ComboBox cmbISP 
      Height          =   315
      Left            =   3480
      TabIndex        =   4
      Text            =   "Combo1"
      Top             =   720
      Width           =   1575
   End
   Begin VB.FileListBox File1 
      Height          =   1065
      Left            =   120
      TabIndex        =   2
      Top             =   3120
      Width           =   2655
   End
   Begin VB.DriveListBox Drive1 
      Height          =   315
      Left            =   120
      TabIndex        =   1
      Top             =   840
      Width           =   2655
   End
   Begin VB.DirListBox Dir1 
      Height          =   1665
      Left            =   120
      TabIndex        =   0
      Top             =   1320
      Width           =   2655
   End
   Begin VB.Label lbl1 
      BackColor       =   &H80000005&
      BackStyle       =   0  'Transparent
      Caption         =   "Choose the file containing the e-mail log"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00C0FFFF&
      Height          =   615
      Left            =   120
      TabIndex        =   5
      Top             =   240
      Width           =   2895
   End
   Begin VB.Label lbl2 
      BackColor       =   &H80000005&
      BackStyle       =   0  'Transparent
      Caption         =   "Choose the ISP"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00C0FFFF&
      Height          =   255
      Left            =   3480
      TabIndex        =   3
      Top             =   360
      Width           =   1815
   End
End
Attribute VB_Name = "frmPredUpload"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Const AOL = "AOL"
Const MICROSOFT = "Microsoft"
Const BELLSOUTH = "BellSouth"
Const EARTHLINK = "EarthLink"
Const SPRINT = "Sprint"

Dim DriveName As String
Private Sub cmdCancel_Click()
    frmPredUpload.Visible = False
    frmWelcome.Visible = True
End Sub

Private Sub cmdOK_Click()
    Dim p As New LogParser
    Dim key As Integer
    Dim log As String
    Dim iMessageCount As Integer
    Dim iMessageNumber As Integer
    Dim iWarrantCount As Integer
    Dim iRFCount As Integer
    Dim getText As Boolean
    Dim i As Integer
    Dim emptyCollection As New Collection
    
    log = ""
    key = -1
    
    If cmbISP.text = AOL Then
        log = "aol.com"
        key = 42
    End If
    
    If cmbISP.text = MICROSOFT Then
        log = "msn.com"
        key = 12
    End If
    
    If cmbISP.text = BELLSOUTH Then
        log = "bellsouth.net"
        key = 23
    End If
    
    If cmbISP.text = EARTHLINK Then
        log = "earthlink.com"
        key = 88
    End If
    
    If cmbISP.text = SPRINT Then
        log = "sprint.com"
        key = 76
    End If
    
    If log = "" Or isp = -1 Then
        MsgBox ("Invalid ISP")
    End If
    
    If cmbISP.text = "nuke" Then
        MsgBox ("Nuking all messages")
        
        nukeMessages
    Else
    
        GenData.GenData File1.path & "\" & log, File1.path & "\" & cmbISP.text & ".enc", key
    
        Dim myMessage As message
        Set myMessage = p.BeginParsing(File1.path & "\" & File1.FileName, key)
    
        iMessageCount = 0
        iWarrantCount = 0
        iRFCount = 0
    
        While Not myMessage Is Nothing
    
        
            iMessageNumber = getAutoNumber("message", "id")
        
            getText = False
        
            '*** check if the sender's address is on the warrant ***
            If checkFrom(myMessage.FromPerson.EmailAddress, iMessageNumber) Then
                getText = True
            End If
        
            '*** check if any of the recipents are on the warrant ***
            i = 1
            While i < myMessage.Recipients.Count
                If checkTo(myMessage.Recipients.Item(i).EmailAddress, iMessageNumber) Then
                    getText = True
                End If
            
                i = i + 1
            Wend
        
            '*** check for the between condition ***
            i = 1
            While i < myMessage.Recipients.Count
                If checkBetween(myMessage.FromPerson.EmailAddress, myMessage.Recipients.Item(i).EmailAddress, iMessageNumber) Then
                    getText = True
                End If
            
                i = i + 1
            Wend
           
            If Not getText Then
                addMessage iMessageNumber, myMessage.FromPerson.EmailAddress, myMessage.DateSent, myMessage.Recipients, Replace(myMessage.Subject, "'", " "), myMessage.SubjectWords, emptyCollection
            Else
                addMessage iMessageNumber, myMessage.FromPerson.EmailAddress, myMessage.DateSent, myMessage.Recipients, Replace(myMessage.Subject, "'", " "), myMessage.SubjectWords, myMessage.MessageWords
                iWarrantCount = iWarrantCount + 1
            End If
        
            '*** check for any red flag words... the function adds them to the database ***
            If checkRF(iMessageNumber, myMessage.MessageWords, myMessage.SubjectWords) Then
                iRFCount = iRFCount + 1
            End If
            iMessageCount = iMessageCount + 1
        
            Set myMessage = p.GetNextMessage
        Wend
    
        MsgBox iMessageCount & " messages uploaded. " & iRFCount & " were red flag messages, and " & iWarrantCount & " were covered by a warrant.", vbExclamation, "Predator upload complete"
    End If
End Sub

Private Sub Dir1_Change()
    File1.path = Dir1.path
End Sub

Private Sub Drive1_Change()
    On Error GoTo poo
    Dir1.path = Drive1.Drive
    DriveName = Drive1.Drive

    GoTo done

poo:
    MsgBox "Sorry, device not available"
    Drive1.Drive = DriveName
done:
End Sub

Private Sub Form_Load()
    DriveName = "c:\"
    
    cmbISP.Clear
    cmbISP.AddItem AOL
    cmbISP.AddItem BELLSOUTH
    cmbISP.AddItem EARTHLINK
    cmbISP.AddItem MICROSOFT
    cmbISP.AddItem SPRINT
    
    cmbISP.ListIndex = 0
    
End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmPredUpload.Visible = False
    frmWelcome.Visible = True

End Sub


Private Sub addMessage(ID As Integer, strFromEmail As String, DateSent As String, strRecipients As Collection, strSubject As String, strSubjectWords As Collection, strMessageWords As Collection)
    Dim addQuery As QueryDef
    Dim dbPredator As Database
    Dim strQuery As String
    Dim i As Integer
    
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "INSERT INTO message (id, from_address, subject, sdate, msg_text) " & _
                "VALUES (" & ID & ", '" & strFromEmail & "', '"

    i = 1
    While i < strSubjectWords.Count + 1
        strQuery = strQuery & Replace(strSubjectWords(i), "'", " ") & " "
        i = i + 1
    Wend
    
    strQuery = strQuery & "', '" & DateSent & "', '"
    
    
    If Not IsNull(strMessageWords) Then
        i = 1
        While i < strMessageWords.Count + 1
            strQuery = strQuery & Replace(strMessageWords(i), "'", " ") & " "
            i = i + 1
        Wend
    End If
    
    strQuery = strQuery & "');"
    
    Set addQuery = dbPredator.CreateQueryDef("", strQuery)
    addQuery.Execute
  
    '*** Add the recipients for the message to the database ***
    i = 1
    
    Dim Email As PersonEmail
    
    While i < strRecipients.Count + 1
        Set Email = strRecipients.Item(i)
        strQuery = "INSERT INTO msg_to(msg, address) " & _
                    "VALUES (" & ID & ", '" & Email.EmailAddress & "');"
        
        Set addQuery = dbPredator.CreateQueryDef("", strQuery)
        addQuery.Execute
          
        i = i + 1
    Wend
  
    
    dbPredator.Close
    
End Sub
Private Function checkFrom(strFrom As String, iMessage As Integer)
    Dim addQuery As QueryDef
    Dim dbPredator As Database
    Dim strQuery As String
    Dim rs As Recordset
        
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT DISTINCT warrant FROM person, addresses, valid_from " & _
                "Where valid_from.person = addresses.person " & _
                "AND addresses.address = '" & strFrom & "';"
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    checkFrom = False
    
    While (Not rs.EOF)
        strQuery = "INSERT INTO msg_warrants (msg, warrant) " & _
                    "VALUES (" & iMessage & ", " & rs.Fields(0) & ");"
                    
        Set addQuery = dbPredator.CreateQueryDef("", strQuery)
        addQuery.Execute
        
        checkFrom = True
        rs.MoveNext
    Wend
    
    dbPredator.Close
End Function

Private Function checkTo(strTo As String, iMessage As Integer)
    Dim addQuery As QueryDef
    Dim dbPredator As Database
    Dim strQuery As String
    Dim rs As Recordset
        
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT DISTINCT warrant FROM person, addresses, valid_to " & _
                "Where valid_to.person = addresses.person " & _
                "AND addresses.address = '" & strTo & "';"
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    checkTo = False
    
    While (Not rs.EOF)
        strQuery = "INSERT INTO msg_warrants (msg, warrant) " & _
                    "VALUES (" & iMessage & ", " & rs.Fields(0) & ");"
                    
        Set addQuery = dbPredator.CreateQueryDef("", strQuery)
        addQuery.Execute
        
        checkTo = True
        rs.MoveNext
    Wend
    
    dbPredator.Close
End Function

Private Function checkBetween(strFrom As String, strTo As String, iMessage As Integer)
    Dim addQuery As QueryDef
    Dim dbPredator As Database
    Dim strQuery As String
    Dim rs As Recordset
        
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT DISTINCT valid_between.warrant FROM person a, addresses b, person c, addresses d, valid_between " & _
                "WHERE valid_between.person_1 = a.id " & _
                "AND b.address = '" & strFrom & "' " & _
                "AND b.person = a.id " & _
                "AND valid_between.person_2 = c.id " & _
                "AND d.address = '" & strTo & "' " & _
                "AND d.person = c.id;"
    
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    checkBetween = False
    
    While (Not rs.EOF)
        strQuery = "INSERT INTO msg_warrants (msg, warrant) " & _
                    "VALUES (" & iMessage & ", " & rs.Fields(0) & ");"
                    
        Set addQuery = dbPredator.CreateQueryDef("", strQuery)
        addQuery.Execute
        
        checkBetween = True
        rs.MoveNext
    Wend
    
    dbPredator.Close
End Function

Private Function checkRF(iMessage As Integer, strMessageWords As Collection, strSubjectWords As Collection)
    Dim addQuery As QueryDef
    Dim dbPredator As Database
    Dim strQuery As String
    Dim rs As Recordset
    Dim i As Integer
        
    Set dbPredator = OpenDatabase(dbFile)
    
    checkRF = False
    
    i = 1
    While i < strMessageWords.Count + 1
        '*** Check for red flag words in message ***
        strQuery = "SELECT id FROM rfwords " & _
                    "WHERE word = '" & Replace(strMessageWords(i), "'", " ") & "';"
        Set rs = dbPredator.OpenRecordset(strQuery)
        
        While (Not rs.EOF)
            checkRF = True
            strQuery = "INSERT INTO msg_rfwords (msg, rfword) " & _
                    "VALUES (" & iMessage & ", " & rs.Fields(0) & ");"
                    
            Set addQuery = dbPredator.CreateQueryDef("", strQuery)
            addQuery.Execute
            
            rs.MoveNext
        Wend
        i = i + 1
    Wend
    
    
    i = 1
    While i < strSubjectWords.Count + 1
    '*** Check for red flag words in subject ***
        strQuery = "SELECT id FROM rfwords " & _
                    "WHERE word = '" & Replace(strSubjectWords(i), "'", " ") & "';"
        Set rs = dbPredator.OpenRecordset(strQuery)
        
        While (Not rs.EOF)
            checkRF = True
            strQuery = "INSERT INTO msg_rfwords (msg, rfword) " & _
                    "VALUES (" & iMessage & ", " & rs.Fields(0) & ");"
                    
            Set addQuery = dbPredator.CreateQueryDef("", strQuery)
            addQuery.Execute
            
            rs.MoveNext
        Wend
        i = i + 1
    Wend
        
    
    dbPredator.Close

End Function

Private Sub nukeMessages()
    Dim deleteQuery As QueryDef
    Dim dbPredator As Database
    Dim strQuery As String

    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "DELETE FROM message"
    Set deleteQuery = dbPredator.CreateQueryDef("", strQuery)
    deleteQuery.Execute

    strQuery = "DELETE FROM msg_rfwords"
    Set deleteQuery = dbPredator.CreateQueryDef("", strQuery)
    deleteQuery.Execute

    strQuery = "DELETE FROM msg_to"
    Set deleteQuery = dbPredator.CreateQueryDef("", strQuery)
    deleteQuery.Execute
    
    strQuery = "DELETE FROM msg_warrants"
    Set deleteQuery = dbPredator.CreateQueryDef("", strQuery)
    deleteQuery.Execute
    
    dbPredator.Close

End Sub
