package br.com.chronos.core.modules.collaboration.domain.exceptions;

import br.com.chronos.core.modules.global.domain.exceptions.NotFoundException;

public class CollaboratorNotFoundException extends NotFoundException{
  public CollaboratorNotFoundException(){
    super("Colaborador nao encontrado");
  }
}
