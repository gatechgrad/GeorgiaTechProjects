VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
  Persistable = 0  'NotPersistable
  DataBindingBehavior = 0  'vbNone
  DataSourceBehavior  = 0  'vbNone
  MTSTransactionMode  = 0  'NotAnMTSObject
END
Attribute VB_Name = "LogParser"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = False

Private LogFile As File
Private ts As TextStream
Private EncryptionKey As Integer

Public Function BeginParsing(FileName As String, key As Integer)
On Error GoTo BeginParsingError

    Dim fso As New FileSystemObject

    EncryptionKey = key
    
    Set LogFile = fso.GetFile(FileName)

    Set ts = LogFile.OpenAsTextStream(ForReading)

    Set BeginParsing = GetNextMessage
    Exit Function
BeginParsingError:
    MsgBox "Couldn't parse: " & FileName
End Function

Public Function GetNextMessage() As message
On Error GoTo GetNextMessageError
    Dim line As String
    Dim msg As New message
    Dim gotMessage As Boolean
    
    gotMessage = False
    line = Decrypt(ts.ReadLine, EncryptionKey)
    
    While Not ts.AtEndOfStream And line <> "END"
        If line = "From:" Then
            'From email format:
            ' From:
            ' email_address
            ' first_name
            ' last_name
            gotMessage = True
            msg.SetSender Decrypt(ts.ReadLine, EncryptionKey), Decrypt(ts.ReadLine, EncryptionKey), Decrypt(ts.ReadLine, EncryptionKey)
        ElseIf line = "To:" Then
            
            msg.AddRecipient Decrypt(ts.ReadLine, EncryptionKey), Decrypt(ts.ReadLine, EncryptionKey), Decrypt(ts.ReadLine, EncryptionKey)
        ElseIf line = "Sent (mm dd yy hr min):" Then
            msg.SetDate Decrypt(ts.ReadLine, EncryptionKey)
        ElseIf line = "Subject:" Then
            msg.SetSubject Decrypt(ts.ReadLine, EncryptionKey)
        ElseIf line = "Text:" Then
            Dim text As String
            text = ""
            
            line = Decrypt(ts.ReadLine, EncryptionKey)
               
            While Not ts.AtEndOfStream And line <> "END"
                text = text & line
                line = Decrypt(ts.ReadLine, EncryptionKey)
            Wend
            
            msg.SetMessage text
        End If
        
        ' Get the next line
        If line <> "END" Then
            line = Decrypt(ts.ReadLine, EncryptionKey)
        End If
    Wend
        
    If gotMessage Then
        Set GetNextMessage = msg
    End If
    
    Exit Function
    
GetNextMessageError:
    CloseLog
End Function

Public Sub CloseLog()
    ts.Close
End Sub


Public Function Decrypt(ByVal Encrypted As String, ByVal key As Integer)
Dim Letter As String
    For i = 1 To Len(Encrypted)
        Letter = Mid$(Encrypted, i, 1)
        Mid$(Encrypted, i, 1) = Chr(Asc(Letter) - key)
    Next i
    Decrypt = Encrypted
End Function










